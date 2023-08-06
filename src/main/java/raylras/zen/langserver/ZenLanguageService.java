package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.langserver.provider.CompletionProvider;
import raylras.zen.langserver.provider.DocumentSymbolProvider;
import raylras.zen.langserver.provider.HoverProvider;
import raylras.zen.langserver.provider.SemanticTokensProvider;
import raylras.zen.util.Compilations;
import raylras.zen.util.Logger;
import raylras.zen.util.PathUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
            manager.checkEnv(PathUtils.toPath(params.getTextDocument().getUri()));
        } catch (Exception e) {
            logger.logError(e, "Failed to open file: {0}", params.getTextDocument().getUri());
        }
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        try {
            CompilationUnit unit = manager.getUnit(PathUtils.toPath(params.getTextDocument().getUri()));
            String source = params.getContentChanges().get(0).getText();
            Compilations.loadUnit(unit, source);
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
            CompilationUnit unit = manager.getUnit(PathUtils.toPath(params.getTextDocument().getUri()));
            SemanticTokens data = SemanticTokensProvider.semanticTokensFull(unit, params);
            return CompletableFuture.completedFuture(data);
        } catch (Exception e) {
            logger.logError(e, "Failed to load semantic tokens: {0}", params.getTextDocument().getUri());
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        try {
            CompilationUnit unit = manager.getUnit(PathUtils.toPath(params.getTextDocument().getUri()));
            Hover hover = HoverProvider.hover(unit, params);
            return CompletableFuture.completedFuture(hover);
        } catch (Exception e) {
            logger.logError(e, "Failed to load hover: ", params.getTextDocument().getUri());
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        return CompletableFuture.completedFuture(null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        try {
            CompilationUnit unit = manager.getUnit(PathUtils.toPath(params.getTextDocument().getUri()));
            List<DocumentSymbol> documentSymbols = DocumentSymbolProvider.documentSymbol(unit, params);
            List<Either<SymbolInformation, DocumentSymbol>> data = documentSymbols.stream()
                    .map(Either::<SymbolInformation, DocumentSymbol>forRight)
                    .collect(Collectors.toList());
            return CompletableFuture.completedFuture(data);
        } catch (Exception e) {
            logger.logError(e, "Failed to load document symbol: ", params.getTextDocument().getUri());
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        try {
            CompilationUnit unit = manager.getUnit(PathUtils.toPath(params.getTextDocument().getUri()));
            List<CompletionItem> data = CompletionProvider.completion(unit, params);
            return CompletableFuture.completedFuture(Either.forLeft(data));
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
                logger.logError(e, "Failed to change watched file: {0}", event);
            }
        });
    }

    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
        params.getEvent().getRemoved().forEach(workspace -> {
            manager.removeWorkspace(PathUtils.toPath(workspace.getUri()));
            logger.logInfo("Removed workspace: {0}", workspace);
        });
        params.getEvent().getAdded().forEach(workspace -> {
            manager.addWorkspace(PathUtils.toPath(workspace.getUri()));
            logger.logInfo("Added workspace: {0}", workspace);
        });
    }

    /* End Workspace Service */

    public void initializeWorkspaces(List<WorkspaceFolder> workspaces) {
        for (WorkspaceFolder folder : workspaces) {
            manager.addWorkspace(PathUtils.toPath(folder.getUri()));
        }
    }

}
