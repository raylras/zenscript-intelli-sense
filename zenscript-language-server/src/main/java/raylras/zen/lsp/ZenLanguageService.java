package raylras.zen.lsp;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.lsp.provider.*;
import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Compilations;
import raylras.zen.util.LogMessages;
import raylras.zen.util.PathUtil;
import raylras.zen.util.l10n.L10N;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ZenLanguageService implements TextDocumentService, WorkspaceService {

    private static final Logger logger = LoggerFactory.getLogger(ZenLanguageService.class);

    private final Set<CompilationEnvironment> environments = new HashSet<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        CompletableFuture.runAsync(() -> {
            lockForWrite();
            try {
                Path path = PathUtil.toPath(params.getTextDocument().getUri());
                LogMessages.start("didOpen", path, logger);
                if (getEnv(path).isEmpty()) {
                    createEnv(path);
                }
                LogMessages.finish("didOpen", path, logger);
            } finally {
                unlockForWrite();
            }
        }).exceptionally(e -> {
            LogMessages.error("didOpen", params, e, logger);
            return null;
        });
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        CompletableFuture.runAsync(() -> {
            lockForWrite();
            try {
                Path path = PathUtil.toPath(params.getTextDocument().getUri());
                LogMessages.start("didChange", path, logger);
                CompilationUnit unit = getUnit(path).orElseThrow();
                String source = params.getContentChanges().get(0).getText();
                Compilations.load(unit, source);
                LogMessages.finish("didChange", path, logger);
            } finally {
                unlockForWrite();
            }
        }).exceptionally(e -> {
            LogMessages.error("didChange", params, e, logger);
            return null;
        });
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        return CompletableFuture.supplyAsync(() -> {
            lockForRead();
            try {
                Path path = PathUtil.toPath(params.getTextDocument().getUri());
                LogMessages.start("completion", path, params.getPosition(), logger);
                CompilationUnit unit = getUnit(path).orElseThrow();
                CompletionList result = CompletionProvider.completion(unit, params);
                LogMessages.finish("completion", path, params.getPosition(), logger);
                return Either.<List<CompletionItem>, CompletionList>forRight(result);
            } finally {
                unlockForRead();
            }
        }).exceptionally(e -> {
            LogMessages.error("completion", params, e, logger);
            return null;
        });
    }

    @Override
    public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
        return CompletableFuture.supplyAsync(() -> {
            LogMessages.start("resolveCompletionItem", logger);
            // do nothing
            LogMessages.finish("resolveCompletionItem", logger);
            return unresolved;
        }).exceptionally(e -> {
            LogMessages.error("resolveCompletionItem", unresolved, e, logger);
            return null;
        });
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        return CompletableFuture.supplyAsync(() -> {
            lockForRead();
            try {
                Path path = PathUtil.toPath(params.getTextDocument().getUri());
                LogMessages.start("hover", path, params.getPosition(), logger);
                CompilationUnit unit = getUnit(path).orElseThrow();
                Hover result = HoverProvider.hover(unit, params);
                LogMessages.finish("hover", path, params.getPosition(), logger);
                return result;
            } finally {
                unlockForRead();
            }
        }).exceptionally(e -> {
            LogMessages.error("hover", params, e, logger);
            return null;
        });
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        return CompletableFuture.supplyAsync(() -> {
            lockForRead();
            try {
                Path path = PathUtil.toPath(params.getTextDocument().getUri());
                LogMessages.start("definition", path, params.getPosition(), logger);
                CompilationUnit unit = getUnit(path).orElseThrow();
                List<LocationLink> result = DefinitionProvider.definition(unit, params);
                LogMessages.finish("definition", path, params.getPosition(), logger);
                return Either.<List<? extends Location>, List<? extends LocationLink>>forRight(result);
            } finally {
                unlockForRead();
            }
        }).exceptionally(e -> {
            LogMessages.error("definition", params, e, logger);
            return null;
        });
    }

    @Override
    public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
        return CompletableFuture.supplyAsync(() -> {
            lockForRead();
            try {
                Path path = PathUtil.toPath(params.getTextDocument().getUri());
                LogMessages.start("references", path, params.getPosition(), logger);
                CompilationUnit unit = getUnit(path).orElseThrow();
                List<Location> result = ReferencesProvider.references(unit, params);
                LogMessages.finish("references", path, params.getPosition(), logger);
                return result;
            } finally {
                unlockForRead();
            }
        }).handle((result, throwable) -> {
            if (throwable != null) {
                LogMessages.error("references", params, throwable, logger);
            }
            return result;
        });
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        return CompletableFuture.supplyAsync(() -> {
            lockForRead();
            try {
                Path path = PathUtil.toPath(params.getTextDocument().getUri());
                LogMessages.start("documentSymbol", path, logger);
                CompilationUnit unit = getUnit(path).orElseThrow();
                List<DocumentSymbol> result = DocumentSymbolProvider.documentSymbol(unit, params);
                LogMessages.finish("documentSymbol", path, logger);
                return result.stream()
                        .map(Either::<SymbolInformation, DocumentSymbol>forRight)
                        .toList();
            } finally {
                unlockForRead();
            }
        }).exceptionally(e -> {
            LogMessages.error("documentSymbol", params, e, logger);
            return null;
        });
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        return CompletableFuture.supplyAsync(() -> {
            lockForRead();
            try {
                Path path = PathUtil.toPath(params.getTextDocument().getUri());
                LogMessages.start("semanticTokensFull", path, logger);
                CompilationUnit unit = getUnit(path).orElseThrow();
                SemanticTokens result = SemanticTokensProvider.semanticTokensFull(unit, params);
                LogMessages.finish("semanticTokensFull", path, logger);
                return result;
            } finally {
                unlockForRead();
            }
        }).exceptionally(e -> {
            LogMessages.error("semanticTokensFull", params, e, logger);
            return null;
        });
    }

    /* End Text Document Service */

    /* Workspace Service */

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {
    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
        CompletableFuture.runAsync(() -> {
            lockForWrite();
            try {
                LogMessages.start("didChangeWatchedFiles", logger);
                for (FileEvent event : params.getChanges()) {
                    Path path = PathUtil.toPath(event.getUri());
                    getEnv(path).ifPresent(env -> {
                        switch (event.getType()) {
                            case Created -> {
                                CompilationUnit unit = env.createUnit(path);
                                Compilations.load(unit);
                            }
                            case Changed -> {
                                CompilationUnit unit = env.getUnit(path);
                                Compilations.load(unit);
                            }
                            case Deleted -> env.removeUnit(path);
                        }
                    });
                }
                LogMessages.finish("didChangeWatchedFiles", logger);
            } catch (Exception e) {
                LogMessages.error("didChangeWatchedFiles", e, logger);
            } finally {
                unlockForWrite();
            }
        });
    }

    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
    }

    /* End Workspace Service */

    private void createEnv(Path documentPath) {
        Path compilationRoot = PathUtil.findUpwardsOrSelf(documentPath, CompilationEnvironment.DEFAULT_ROOT_DIRECTORY);
        CompilationEnvironment env = new CompilationEnvironment(compilationRoot);
        Compilations.load(env);
        environments.add(env);
        checkDzs(env);
    }

    private Optional<CompilationEnvironment> getEnv(Path documentPath) {
        return environments.stream()
                .filter(env -> env.containsUnit(documentPath))
                .findFirst();
    }

    private Optional<CompilationUnit> getUnit(Path documentPath) {
        return getEnv(documentPath).map(env -> env.getUnit(documentPath));
    }

    private void checkDzs(CompilationEnvironment env) {
        if (Files.exists(env.getGeneratedRoot())) {
            logger.info("Cannot find .dzs file directory of environment: {}", env);
            LogMessages.info(L10N.getString("dzs_not_found"), ZenLanguageServer.getClient());
        }
    }

    private void lockForRead() {
        lock.readLock().lock();
    }

    private void unlockForRead() {
        lock.readLock().unlock();
    }

    private void lockForWrite() {
        lock.writeLock().lock();
    }

    private void unlockForWrite() {
        lock.writeLock().unlock();
    }

}
