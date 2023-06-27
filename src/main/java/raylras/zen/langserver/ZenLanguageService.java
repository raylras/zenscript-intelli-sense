package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.langserver.provider.CompletionProvider;
import raylras.zen.langserver.provider.SemanticTokensProvider;
import raylras.zen.util.Logger;
import raylras.zen.util.Utils;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ZenLanguageService implements TextDocumentService, WorkspaceService {

    private static final Logger logger = Logger.getLogger("service");

    private final WorkspaceManager manager;

    public ZenLanguageService() {
        this.manager = new WorkspaceManager();
    }

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        try {
            manager.checkEnv(Utils.toPath(params.getTextDocument().getUri()));
        } catch (Exception e) {
            logger.logError(e, "Failed to open file: {0}", params.getTextDocument().getUri());
        }
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        try {
            CompilationUnit unit = manager.getUnit(Utils.toPath(params.getTextDocument().getUri()));
            String source = params.getContentChanges().get(0).getText();
            unit.load(source);
        } catch (Exception e) {
            logger.logError(e, "Failed to change file: {0}", params.getTextDocument().getUri());
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
            CompilationUnit unit = manager.getUnit(Utils.toPath(params.getTextDocument().getUri()));
            SemanticTokens data = SemanticTokensProvider.semanticTokensFull(unit, params);
            return CompletableFuture.completedFuture(data);
        } catch (Exception e) {
            logger.logError(e, "Failed to load semantic tokens: {0}", params.getTextDocument().getUri());
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        return CompletableFuture.completedFuture(null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        try {
            CompilationUnit unit = manager.getUnit(Utils.toPath(params.getTextDocument().getUri()));
            CompletionList data = CompletionProvider.completion(unit, params);
            return CompletableFuture.completedFuture(Either.forRight(data));
        } catch (Exception e) {
            logger.logError(e, "Failed to load completion: ", params.getTextDocument().getUri());
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
                Path documentPath = Utils.toPath(event.getUri());
                manager.checkEnv(documentPath);
                CompilationEnvironment env = manager.getEnv(documentPath);
                switch (event.getType()) {
                    case Created:
                        env.createUnit(documentPath);
                        break;
                    case Changed:
                        env.getUnit(documentPath).load();
                        break;
                    case Deleted:
                        env.removeUnit(documentPath);
                        break;
                }
            } catch (Exception e) {
                logger.logError(e, "Failed to change watched file: {0}", event);
            }
        });
    }

    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
        params.getEvent().getRemoved().forEach(workspace -> {
            manager.removeWorkspace(Utils.toPath(workspace.getUri()));
            logger.logInfo("Removed workspace: {0}", workspace);
        });
        params.getEvent().getAdded().forEach(workspace -> {
            manager.addWorkspace(Utils.toPath(workspace.getUri()));
            logger.logInfo("Added workspace: {0}", workspace);
        });
    }

    /* End Workspace Service */

    public void initializeWorkspaces(List<WorkspaceFolder> workspaces) {
        for (WorkspaceFolder folder : workspaces) {
            manager.addWorkspace(Utils.toPath(folder.getUri()));
        }
    }

}
