package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.CompilationUnit;
import raylras.zen.langserver.provider.*;
import raylras.zen.util.Compilations;
import raylras.zen.util.PathUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ZenLanguageService implements TextDocumentService, WorkspaceService {

    private static final Logger logger = LoggerFactory.getLogger(ZenLanguageService.class);

    private static LanguageClient client;
    private final WorkspaceManager workspaceManager;

    public ZenLanguageService() {
        this.workspaceManager = new WorkspaceManager();
    }

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        try {
            workspaceManager.createEnvIfNotExists(params.getTextDocument().getUri());
        } catch (Exception e) {
            logger.error("Failed to process 'didOpen' event: {}", params, e);
        }
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        try (Document doc = workspaceManager.openAsWrite(params.getTextDocument())){
            String source = params.getContentChanges().get(0).getText();
            Compilations.reload(doc, source);
        } catch (Exception e) {
            logger.error("Failed to process 'didChange' event: {}", params, e);
        }
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())){
            return SemanticTokensProvider.semanticTokensFull(doc, params);
        } catch (Exception e) {
            logger.error("Failed to process 'semanticTokensFull' request: {}", params, e);
            return SemanticTokensProvider.empty();
        }
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())) {
            return HoverProvider.hover(doc, params);
        } catch (Exception e) {
            logger.error("Failed to process 'hover' request: {}", params, e);
            return HoverProvider.empty();
        }
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())){
            return DefinitionProvider.definition(doc, params);
        } catch (Exception e) {
            logger.error("Failed to process 'definition' request: {}", params, e);
            return DefinitionProvider.empty();
        }
    }

    @Override
    public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())){
            return ReferencesProvider.references(doc, params);
        } catch (Exception e) {
            logger.error("Failed to process 'documentSymbol' request: {}", params, e);
            return ReferencesProvider.empty();
        }
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())) {
            return DocumentSymbolProvider.documentSymbol(doc, params);
        } catch (Exception e) {
            logger.error("Failed to process 'documentSymbol' request: {}", params, e);
            return DocumentSymbolProvider.empty();
        }
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())) {
            return CompletionProvider.completion(doc, params);
        } catch (Exception e) {
            logger.error("Failed to process 'completion' request: {}", params, e);
            return CompletionProvider.empty();
        }
    }

    @Override
    public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
        return CompletableFuture.completedFuture(null);
    }

    /* End Text Document Service */

    /* Workspace Service */

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {
    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
        params.getChanges().forEach(event -> {
            try {
                workspaceManager.createEnvIfNotExists(event.getUri());
                workspaceManager.getEnv(event.getUri()).ifPresent(env -> {
                    Path documentPath = PathUtils.toPath(event.getUri());
                    switch (event.getType()) {
                        case Created -> {
                            CompilationUnit unit = env.createUnit(documentPath);
                            Compilations.loadUnit(unit);
                        }
                        case Changed -> {
                            CompilationUnit unit = env.getUnit(documentPath);
                            Compilations.loadUnit(unit);
                        }
                        case Deleted -> {
                            env.removeUnit(documentPath);
                        }
                    }
                });
            } catch (Exception e) {
                logger.error("Failed to process 'didChangeWatchedFiles' event: {}", event, e);
            }
        });
    }

    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
        params.getEvent().getRemoved().forEach(workspace -> {
            workspaceManager.removeWorkspace(workspace);
            logger.info("Workspace folder removed: {}", workspace);
        });
        params.getEvent().getAdded().forEach(workspace -> {
            workspaceManager.addWorkspace(workspace);
            logger.info("Workspace folder added: {}", workspace);
        });
    }

    /* End Workspace Service */

    public static LanguageClient getClient() {
        return client;
    }

    public static void setClient(LanguageClient client) {
        ZenLanguageService.client = client;
    }

    public static void showMessage(MessageParams messageParams) {
        if (client != null) {
            client.showMessage(messageParams);
        }
    }

    public void initializeWorkspaces(List<WorkspaceFolder> workspaces) {
        if (workspaces != null) {
            workspaces.forEach(workspace -> {
                workspaceManager.addWorkspace(workspace);
                logger.info("{}", workspace);
            });
        }
    }

}
