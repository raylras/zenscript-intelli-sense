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

    public void createEnv(Path documentPath) {
        lockForWrite();
        try {
            Path compilationRoot = PathUtil.findUpwardsOrSelf(documentPath, CompilationEnvironment.DEFAULT_ROOT_DIRECTORY);
            CompilationEnvironment env = new CompilationEnvironment(compilationRoot);
            Compilations.load(env);
            environments.add(env);
            checkDzs(env);
        } finally {
            unlockForWrite();
        }
    }

    public Optional<CompilationEnvironment> getEnv(Path documentPath) {
        lockForRead();
        try {
            return environments.stream()
                    .filter(env -> PathUtil.isSubPath(documentPath, env.getRoot()))
                    .findFirst();
        } finally {
            unlockForRead();
        }
    }

    public Optional<CompilationUnit> getUnit(Path documentPath) {
        lockForRead();
        try {
            return getEnv(documentPath).map(env -> env.getUnit(documentPath));
        } finally {
            unlockForRead();
        }
    }

    private void checkDzs(CompilationEnvironment env) {
        if (env.getGeneratedRoot().isEmpty()) {
            logger.info("Cannot find .dzs file directory of environment: {}", env);
            LogMessages.info(L10N.getString("dzs_not_found"), ZenLanguageServer.getClient());
        }
    }

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        CompletableFuture.runAsync(() -> {
            Path path = PathUtil.toPath(params.getTextDocument().getUri());
            LogMessages.request("didOpen", path, logger);
            if (getEnv(path).isEmpty()) {
                createEnv(path);
            }
        }).exceptionally(e -> {
            LogMessages.error("didOpen", params, e, logger);
            return null;
        });
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        CompletableFuture.runAsync(() -> {
            Path path = PathUtil.toPath(params.getTextDocument().getUri());
            LogMessages.request("didChange", path, logger);
            CompilationUnit unit = getUnit(path).orElseThrow();
            lockForWrite();
            try {
                String source = params.getContentChanges().get(0).getText();
                Compilations.load(unit, source);
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
            Path path = PathUtil.toPath(params.getTextDocument().getUri());
            LogMessages.request("completion", path, params.getPosition(), logger);
            CompilationUnit unit = getUnit(path).orElseThrow();
            lockForRead();
            try {
                CompletionList result = CompletionProvider.completion(unit, params);
                LogMessages.response("completion", path, params.getPosition(), logger);
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
            LogMessages.request("resolveCompletionItem", logger);
            // do nothing
            LogMessages.response("resolveCompletionItem", logger);
            return unresolved;
        }).exceptionally(e -> {
            LogMessages.error("resolveCompletionItem", unresolved, e, logger);
            return null;
        });
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        return CompletableFuture.supplyAsync(() -> {
            Path path = PathUtil.toPath(params.getTextDocument().getUri());
            LogMessages.request("hover", path, params.getPosition(), logger);
            CompilationUnit unit = getUnit(path).orElseThrow();
            lockForRead();
            try {
                Hover result = HoverProvider.hover(unit, params);
                LogMessages.response("hover", path, params.getPosition(), logger);
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
            Path path = PathUtil.toPath(params.getTextDocument().getUri());
            LogMessages.request("definition", path, params.getPosition(), logger);
            CompilationUnit unit = getUnit(path).orElseThrow();
            lockForRead();
            try {
                List<LocationLink> result = DefinitionProvider.definition(unit, params);
                LogMessages.response("definition", path, params.getPosition(), logger);
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
            Path path = PathUtil.toPath(params.getTextDocument().getUri());
            LogMessages.request("references", path, params.getPosition(), logger);
            CompilationUnit unit = getUnit(path).orElseThrow();
            lockForRead();
            try {
                List<Location> result = ReferencesProvider.references(unit, params);
                LogMessages.response("references", path, params.getPosition(), logger);
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
            Path path = PathUtil.toPath(params.getTextDocument().getUri());
            LogMessages.request("documentSymbol", path, logger);
            CompilationUnit unit = getUnit(path).orElseThrow();
            lockForRead();
            try {
                List<DocumentSymbol> result = DocumentSymbolProvider.documentSymbol(unit, params);
                LogMessages.response("documentSymbol", path, logger);
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
            Path path = PathUtil.toPath(params.getTextDocument().getUri());
            LogMessages.request("semanticTokensFull", path, logger);
            CompilationUnit unit = getUnit(path).orElseThrow();
            lockForRead();
            try {
                SemanticTokens result = SemanticTokensProvider.semanticTokensFull(unit, params);
                LogMessages.response("semanticTokensFull", path, logger);
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
            LogMessages.request("didChangeWatchedFiles", logger);
            lockForWrite();
            try {
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
