package raylras.zen.lsp;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.lsp.provider.*;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Document;
import raylras.zen.model.Compilations;
import raylras.zen.util.PathUtil;
import raylras.zen.util.StopWatch;
import raylras.zen.util.Watcher;

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
            Path path = PathUtil.toPath(params.getTextDocument().getUri());
            StopWatch stopWatch = StopWatch.createAndStart();
            workspaceManager.createEnvIfNotExists(path);
            stopWatch.stop();
            logger.info("didOpen {} [{}]", path.getFileName(), stopWatch.getFormattedMillis());
        } catch (Exception e) {
            logger.error("didOpen {}", params, e);
        }
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        try (Document doc = workspaceManager.openAsWrite(params.getTextDocument())){
            doc.getUnit().ifPresent(unit -> {
                StopWatch stopWatch = StopWatch.createAndStart();
                String source = params.getContentChanges().get(0).getText();
                Compilations.load(unit, source);
                stopWatch.stop();
                logger.trace("didChange {} [{}]", unit.getPath().getFileName(), stopWatch.getFormattedMillis());
            });
        } catch (Exception e) {
            logger.error("didChange {}", params, e);
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
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())) {
            return CompletableFuture.supplyAsync(() -> doc.getUnit().flatMap(unit -> {
                        StopWatch stopWatch = StopWatch.createAndStart();
                        var optional = SemanticTokensProvider.semanticTokensFull(unit, params);
                        stopWatch.stop();
                        if (optional.isPresent()) {
                            logger.info("semanticTokensFull {} [{}]", unit.getPath().getFileName(), stopWatch.getFormattedMillis());
                        }
                        return optional;
                    }).orElse(null));
        } catch (Exception e) {
            logger.error("semanticTokensFull {}", params, e);
            return emptyFuture();
        }
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())) {
            return CompletableFuture.supplyAsync(() -> doc.getUnit().flatMap(unit -> {
                var watcher = Watcher.watch(() -> HoverProvider.hover(unit, params));
                if (watcher.isResultPresent()) {
                    int line = params.getPosition().getLine() + 1;
                    int column = params.getPosition().getCharacter();
                    logger.info("hover {} at ({},{}) [{}]", unit.getPath().getFileName(), line, column, watcher.getElapsedMillis());
                }
                return watcher.getResult();
            }).orElse(null));
        } catch (Exception e) {
            logger.error("hover {}", params, e);
            return emptyFuture();
        }
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())) {
            return CompletableFuture.supplyAsync(() -> doc.getUnit().flatMap(unit -> {
                var watcher = Watcher.watch(() -> DefinitionProvider.definition(unit, params));
                if (watcher.isResultPresent()) {
                    int line = params.getPosition().getLine() + 1;
                    int column = params.getPosition().getCharacter();
                    logger.info("definition {} at ({},{}) [{}]", unit.getPath().getFileName(), line, column, watcher.getElapsedMillis());
                }
                return watcher.getResult();
            }).orElse(null));
        } catch (Exception e) {
            logger.error("definition {}", params, e);
            return emptyFuture();
        }
    }

    @Override
    public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())){
            return CompletableFuture.supplyAsync(() -> doc.getUnit().flatMap(unit -> {
                var watcher = Watcher.watch(() -> ReferencesProvider.references(unit, params));
                if (watcher.isResultPresent()) {
                    int line = params.getPosition().getLine() + 1;
                    int column = params.getPosition().getCharacter();
                    logger.info("references {} at ({},{}) [{}]", unit.getPath().getFileName(), line, column, watcher.getElapsedMillis());
                }
                return watcher.getResult();
            }).orElse(null));
        } catch (Exception e) {
            logger.error("references {}", params, e);
            return emptyFuture();
        }
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())) {
            return CompletableFuture.supplyAsync(() -> doc.getUnit().flatMap(unit -> {
                var watcher = Watcher.watch(() -> DocumentSymbolProvider.documentSymbol(unit, params));
                if (watcher.isResultPresent()) {
                    logger.info("documentSymbol {} [{}]", unit.getPath().getFileName(), watcher.getElapsedMillis());
                }
                return watcher.getResult();
            }).orElse(null));
        } catch (Exception e) {
            logger.error("documentSymbol {}", params, e);
            return emptyFuture();
        }
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        try (Document doc = workspaceManager.openAsRead(params.getTextDocument())) {
            return CompletableFuture.supplyAsync(() -> doc.getUnit().flatMap(unit -> {
                var watcher = Watcher.watch(() -> CompletionProvider.completion(unit, params));
                if (watcher.isResultPresent()) {
                    int line = params.getPosition().getLine() + 1;
                    int column = params.getPosition().getCharacter();
                    logger.info("completion {} at ({},{}) [{}]", unit.getPath().getFileName(), line, column, watcher.getElapsedMillis());
                }
                return watcher.getResult();
            }).orElse(null));
        } catch (Exception e) {
            logger.error("completion {}", params, e);
            return emptyFuture();
        }
    }

    @Override
    public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
        return emptyFuture();
    }

    /* End Text Document Service */

    /* Workspace Service */

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {
    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
        StopWatch stopWatch = StopWatch.createAndStart();
        for (FileEvent event : params.getChanges()) {
            try {
                Path documentPath = PathUtil.toPath(event.getUri());
                workspaceManager.createEnvIfNotExists(documentPath);
                workspaceManager.getEnv(documentPath).ifPresent(env -> {
                    switch (event.getType()) {
                        case Created -> {
                            CompilationUnit unit = env.createUnit(documentPath);
                            Compilations.load(unit);
                        }
                        case Changed -> {
                            CompilationUnit unit = env.getUnit(documentPath);
                            Compilations.load(unit);
                        }
                        case Deleted -> {
                            env.removeUnit(documentPath);
                        }
                    }
                });
            } catch (Exception e) {
                logger.error("didChangeWatchedFiles {}", event, e);
            }
        }
        stopWatch.stop();
        List<String> changes = params.getChanges().stream()
                .map(fileEvent -> PathUtil.getFileName(fileEvent.getUri()))
                .toList();
        logger.info("didChangeWatchedFiles {} [{}]", changes, stopWatch.getFormattedMillis());
    }

    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
        params.getEvent().getRemoved().forEach(workspace -> {
            workspaceManager.removeWorkspace(workspace);
            logger.info("Removed Workspace folder: {}", workspace);
        });
        params.getEvent().getAdded().forEach(workspace -> {
            workspaceManager.addWorkspace(workspace);
            logger.info("Added Workspace folder: {}", workspace);
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

    private <T> CompletableFuture<T> emptyFuture() {
        return CompletableFuture.completedFuture(null);
    }

}
