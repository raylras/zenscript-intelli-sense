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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ZenLanguageService implements TextDocumentService, WorkspaceService {

    private static final Logger logger = LoggerFactory.getLogger(ZenLanguageService.class);

    private static LanguageClient client;
    private final WorkspaceManager manager;
    private final ExecutorService mainExecutor;

    public ZenLanguageService(ExecutorService mainExecutor) {
        this.mainExecutor = mainExecutor;
        this.manager = new WorkspaceManager();
    }

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        CompletableFuture.runAsync(() -> {
            try {
                Path path = PathUtils.toPath(params.getTextDocument().getUri());
                manager.checkEnv(path);
            } catch (Exception e) {
                logger.error("Failed to process 'didOpen' event: {}", params, e);
            }
        }, mainExecutor);
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        CompletableFuture.runAsync(() -> {
            Lock lock = null;
            try {
                Path path = PathUtils.toPath(params.getTextDocument().getUri());
                CompilationEnvironment env = manager.getEnv(path);
                lock = env.getLock().writeLock();
                lock.lock();
                CompilationUnit unit = env.getUnit(path);
                String source = params.getContentChanges().get(0).getText();
                Compilations.loadUnit(unit, source);
            } catch (Exception e) {
                logger.error("Failed to process 'didChange' event: {}", params, e);
            } finally {
                if(lock != null) {
                    lock.unlock();
                }
            }
        }, mainExecutor);
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        return computeInReadAction("semanticTokensFull", params.getTextDocument().getUri(), unit -> {
            return SemanticTokensProvider.semanticTokensFull(unit, params);
        });
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        return computeInReadAction("hover", params.getTextDocument().getUri(), unit -> {
            return HoverProvider.hover(unit, params);
        });
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        return computeInReadAction("definition", params.getTextDocument().getUri(), unit -> {
            List<LocationLink> definition = DefinitionProvider.definition(unit, params);
            return Either.forRight(definition);
        });
    }

    @Override
    public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
        return computeInReadAction("references", params.getTextDocument().getUri(), unit -> {
            return ReferencesProvider.references(unit, params);
        });
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        return computeInReadAction("documentSymbol", params.getTextDocument().getUri(), unit -> {
            List<DocumentSymbol> documentSymbols = DocumentSymbolProvider.documentSymbol(unit, params);
            return documentSymbols.stream().map(Either::<SymbolInformation, DocumentSymbol>forRight).collect(Collectors.toList());
        });
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        return computeInReadAction("completion", params.getTextDocument().getUri(), unit -> {
            List<CompletionItem> data = CompletionProvider.completion(unit, params);
            return Either.forLeft(data);
        });
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
        CompletableFuture.runAsync(() -> {
            params.getChanges().forEach(event -> {
                Lock lock = null;
                try {
                    Path documentPath = PathUtils.toPath(event.getUri());
                    manager.checkEnv(documentPath);
                    CompilationEnvironment env = manager.getEnv(documentPath);
                    lock = env.getLock().writeLock();
                    lock.lock();
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
                } catch (Exception e) {
                    logger.error("Failed to process 'didChangeWatchedFiles' event: {}", event, e);
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            });
        }, mainExecutor);
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

    private <T> CompletableFuture<T> computeInReadAction(String processName, CompilationEnvironment env, Supplier<T> action) {
        return CompletableFuture.supplyAsync(() -> {
            env.getLock().readLock().lock();
            try {
                return action.get();
            } catch (Exception e) {
                logger.error("failed to process '{}'", processName, e);
                return null;
            } finally {
                env.getLock().readLock().unlock();
            }
        }, mainExecutor);
    }

    private <T> CompletableFuture<T> computeInReadAction(String processName, String uri, Function<CompilationUnit, T> action) {
        Path path;
        try {
            path = PathUtils.toPath(uri);
        } catch (Exception e) {
            logger.error("failed to process '{}': could not resolve path uri: {}", processName, uri);
            return CompletableFuture.completedFuture(null);
        }
        return computeInReadAction(processName, path, action);
    }

    private <T> CompletableFuture<T> computeInReadAction(String processName, Path path, Function<CompilationUnit, T> action) {
        return CompletableFuture.supplyAsync(() -> {
            return manager.getEnv(path);
        }, mainExecutor).thenCompose(env -> {
            if (env == null) {
                logger.error("failed to process '{}': could not find compilation environment at: {}", processName, path);
                return CompletableFuture.completedFuture(null);
            }
            return computeInReadAction(processName, env, () -> {
                CompilationUnit unit = env.getUnit(path);
                if (unit == null) {
                    logger.error("failed to process '{}': could not find compilation unit at: {}", processName, path);
                    return null;
                }
                return action.apply(unit);
            });
        });
    }

}
