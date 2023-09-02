package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.langserver.provider.*;
import raylras.zen.util.Compilations;
import raylras.zen.util.PathUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ZenLanguageService implements TextDocumentService, WorkspaceService {

    private static final Logger logger = LoggerFactory.getLogger(ZenLanguageService.class);

    private static LanguageClient client;
    private final WorkspaceManager manager;

    public ZenLanguageService() {
        this.manager = new WorkspaceManager();
    }

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        try {
            Path path = PathUtils.toPath(params.getTextDocument().getUri());
            manager.checkEnv(path);
        } catch (Exception e) {
            logger.error("Failed to process 'didOpen' event: {}", params, e);
        }
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        try {
            Path path = PathUtils.toPath(params.getTextDocument().getUri());
            CompilationUnit unit = manager.getUnit(path);
            String source = params.getContentChanges().get(0).getText();
            Compilations.loadUnit(unit, source);
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
        try {
            Path path = PathUtils.toPath(params.getTextDocument().getUri());
            CompilationUnit unit = manager.getUnit(path);
            SemanticTokens data = SemanticTokensProvider.semanticTokensFull(unit, params);
            return CompletableFuture.completedFuture(data);
        } catch (Exception e) {
            logger.error("Failed to process 'semanticTokensFull' request: {}", params, e);
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        try {
            Path path = PathUtils.toPath(params.getTextDocument().getUri());
            CompilationUnit unit = manager.getUnit(path);
            Hover hover = HoverProvider.hover(unit, params);
            return CompletableFuture.completedFuture(hover);
        } catch (Exception e) {
            logger.error("Failed to process 'hover' request: {}", params, e);
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        try {
            Path path = PathUtils.toPath(params.getTextDocument().getUri());
            CompilationUnit unit = manager.getUnit(path);
            List<LocationLink> definition = DefinitionProvider.definition(unit, params);
            return CompletableFuture.completedFuture(Either.forRight(definition));
        } catch (Exception e) {
            logger.error("Failed to process 'definition' request: {}", params, e);
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        try {
            Path path = PathUtils.toPath(params.getTextDocument().getUri());
            CompilationUnit unit = manager.getUnit(path);
            List<DocumentSymbol> documentSymbols = DocumentSymbolProvider.documentSymbol(unit, params);
            List<Either<SymbolInformation, DocumentSymbol>> data = documentSymbols.stream()
                    .map(Either::<SymbolInformation, DocumentSymbol>forRight)
                    .collect(Collectors.toList());
            return CompletableFuture.completedFuture(data);
        } catch (Exception e) {
            logger.error("Failed to process 'documentSymbol' request: {}", params, e);
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        try {
            Path path = PathUtils.toPath(params.getTextDocument().getUri());
            CompilationUnit unit = manager.getUnit(path);
            List<CompletionItem> data = CompletionProvider.completion(unit, params);
            return CompletableFuture.completedFuture(Either.forLeft(data));
        } catch (Exception e) {
            logger.error("Failed to process 'completion' request: {}", params, e);
            return CompletableFuture.completedFuture(null);
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
                Path documentPath = PathUtils.toPath(event.getUri());
                manager.checkEnv(documentPath);
                CompilationEnvironment env = manager.getEnv(documentPath);
                switch (event.getType()) {
                    case Created: {
                        CompilationUnit unit = env.createUnit(documentPath);
                        Compilations.loadUnit(unit);
                        break;
                    }
                    case Changed: {
                        CompilationUnit unit = env.getUnit(documentPath);
                        Compilations.loadUnit(unit);
                        break;
                    }
                    case Deleted: {
                        env.removeUnit(documentPath);
                        break;
                    }
                }
            } catch (Exception e) {
                logger.error("Failed to process 'didChangeWatchedFiles' event: {}", event, e);
            }
        });
    }

    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
        params.getEvent().getRemoved().forEach(workspace -> {
            manager.removeWorkspace(PathUtils.toPath(workspace.getUri()));
            logger.error("Workspace folder removed: {}", workspace);
        });
        params.getEvent().getAdded().forEach(workspace -> {
            manager.addWorkspace(PathUtils.toPath(workspace.getUri()));
            logger.error("Workspace folder added: {}", workspace);
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
        for (WorkspaceFolder folder : workspaces) {
            manager.addWorkspace(PathUtils.toPath(folder.getUri()));
        }
    }

}
