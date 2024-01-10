package raylras.zen.lsp

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.TextDocumentService
import org.eclipse.lsp4j.services.WorkspaceService
import org.slf4j.LoggerFactory
import raylras.zen.lsp.StandardIOLauncher.CLIENT
import raylras.zen.lsp.provider.*
import raylras.zen.model.*
import raylras.zen.util.LogMessage
import raylras.zen.util.findUpwardsOrSelf
import raylras.zen.util.info
import raylras.zen.util.l10n.L10N
import raylras.zen.util.toPath
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.io.path.notExists
import kotlin.time.measureTime

class ZenLanguageService : TextDocumentService, WorkspaceService {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val environments = HashSet<CompilationEnvironment>()
    private val lock = ReentrantReadWriteLock()

    /* Text Document Service */
    override fun didOpen(params: DidOpenTextDocumentParams) {
        CompletableFuture.runAsync {
            val path = toPath(params.textDocument.uri)
            val logMessage = LogMessage("didOpen", path, logger = logger)
            logMessage.start()
            lockForWrite()
            try {
                measureTime {
                    getEnv(path) ?: createEnv(path)
                }.also {
                    logMessage.finish(it)
                }
            } finally {
                unlockForWrite()
            }
        }
    }

    override fun didChange(params: DidChangeTextDocumentParams) {
        CompletableFuture.runAsync {
            val path = toPath(params.textDocument.uri)
            val logMessage = LogMessage("didChange", path, logger = logger)
            logMessage.start()
            lockForWrite()
            try {
                measureTime {
                    val unit = getUnit(path)
                    val source = params.contentChanges[0].text
                    unit?.load(source)
                }.also {
                    logMessage.finish(it)
                }
            } finally {
                unlockForWrite()
            }
        }
    }

    override fun didClose(params: DidCloseTextDocumentParams) {
    }

    override fun didSave(params: DidSaveTextDocumentParams) {
    }

    override fun completion(params: CompletionParams): CompletableFuture<Either<List<CompletionItem>, CompletionList>> {
        return CompletableFuture.supplyAsync {
            val path = toPath(params.textDocument.uri)
            val logMessage = LogMessage("completion", path, params.position, logger)
            logMessage.start()
            lockForRead()
            try {
                val result: CompletionList?
                measureTime {
                    result = CompletionProvider.completion(getUnit(path), params)
                }.also {
                    logMessage.finish(it)
                }
                Either.forRight(result)
            } finally {
                unlockForRead()
            }
        }
    }

    override fun resolveCompletionItem(unresolved: CompletionItem): CompletableFuture<CompletionItem> {
        return CompletableFuture.supplyAsync {
            val logMessage = LogMessage("resolveCompletionItem", logger = logger)
            logMessage.start()
            measureTime {
                // do something
            }.also {
                logMessage.finish(it)
            }
            unresolved
        }
    }

    override fun hover(params: HoverParams): CompletableFuture<Hover?> {
        return CompletableFuture.supplyAsync {
            val path = toPath(params.textDocument.uri)
            val logMessage = LogMessage("hover", path, params.position, logger)
            logMessage.start()
            lockForRead()
            try {
                val result: Hover?
                measureTime {
                    result = HoverProvider.hover(getUnit(path), params)
                }.also {
                    logMessage.finish(it)
                }
                result
            } finally {
                unlockForRead()
            }
        }
    }

    override fun definition(params: DefinitionParams): CompletableFuture<Either<List<Location>, List<LocationLink>>> {
        return CompletableFuture.supplyAsync {
            val path = toPath(params.textDocument.uri)
            val logMessage = LogMessage("definition", path, params.position, logger)
            logMessage.start()
            lockForRead()
            try {
                val result: List<LocationLink>?
                measureTime {
                    result = DefinitionProvider.definition(getUnit(path), params)
                }.also {
                    logMessage.finish(it)
                }
                Either.forRight(result)
            } finally {
                unlockForRead()
            }
        }
    }

    override fun references(params: ReferenceParams): CompletableFuture<List<Location>> {
        return CompletableFuture.supplyAsync {
            val path = toPath(params.textDocument.uri)
            val logMessage = LogMessage("references", path, params.position, logger)
            logMessage.start()
            lockForRead()
            try {
                val result: List<Location>?
                measureTime {
                    result = ReferencesProvider.references(getUnit(path), params)
                }.also {
                    logMessage.finish(it)
                }
                result
            } finally {
                unlockForRead()
            }
        }
    }

    override fun documentSymbol(params: DocumentSymbolParams): CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> {
        return CompletableFuture.supplyAsync {
            val path = toPath(params.textDocument.uri)
            val logMessage = LogMessage("documentSymbol", path, logger = logger)
            logMessage.start()
            lockForRead()
            val result: List<DocumentSymbol>?
            try {
                measureTime {
                    result = DocumentSymbolProvider.documentSymbol(getUnit(path), params)
                }.also {
                    logMessage.finish(it)
                }
                result?.map { Either.forRight(it) }
            } finally {
                unlockForRead()
            }
        }
    }

    override fun semanticTokensFull(params: SemanticTokensParams): CompletableFuture<SemanticTokens> {
        return CompletableFuture.supplyAsync {
            val path = toPath(params.textDocument.uri)
            val logMessage = LogMessage("semanticTokensFull", path, logger = logger)
            logMessage.start()
            lockForRead()
            val result: SemanticTokens?
            try {
                measureTime {
                    result = SemanticTokensProvider.semanticTokensFull(getUnit(path), params)
                }.also {
                    logMessage.finish(it)
                }
                result
            } finally {
                unlockForRead()
            }
        }
    }

    override fun inlayHint(params: InlayHintParams): CompletableFuture<List<InlayHint>> {
        return CompletableFuture.supplyAsync {
            val path = toPath(params.textDocument.uri)
            val logMessage = LogMessage("inlayHint", path, logger = logger)
            logMessage.start()
            lockForRead()
            val result: List<InlayHint>?
            try {
                measureTime {
                    result = InlayHintProvider.inlayHint(getUnit(path), params)
                }.also {
                    logMessage.finish(it)
                }
                result
            } finally {
                unlockForRead()
            }
        }
    }

    /* End Text Document Service */ /* Workspace Service */
    override fun didChangeConfiguration(params: DidChangeConfigurationParams) {
    }

    override fun didChangeWatchedFiles(params: DidChangeWatchedFilesParams) {
        CompletableFuture.runAsync {
            val logMessage = LogMessage("didChangeWatchedFiles", logger = logger)
            logMessage.start()
            lockForWrite()
            try {
                measureTime {
                    for (event in params.changes) {
                        val path = toPath(event.uri)
                        getEnv(path)?.let {
                            when (event.type) {
                                FileChangeType.Created -> {
                                    val unit = createUnit(path, it)
                                    unit.load()
                                }

                                FileChangeType.Changed -> {
                                    val unit = it.unitMap[path]
                                    unit?.load()
                                }

                                FileChangeType.Deleted -> it.unitMap.remove(path)

                                else -> {}
                            }
                        }
                    }
                }.also {
                    logMessage.finish(it)
                }
            } finally {
                unlockForWrite()
            }
        }
    }

    override fun didChangeWorkspaceFolders(params: DidChangeWorkspaceFoldersParams) {
    }

    /* End Workspace Service */

    private fun createEnv(documentPath: Path) {
        val compilationRoot = findUpwardsOrSelf(documentPath, DEFAULT_ROOT_DIRECTORY)
        val env = CompilationEnvironment(compilationRoot)
        env.load()
        environments.add(env)
        checkDzs(env)
    }

    private fun getEnv(documentPath: Path): CompilationEnvironment? {
        return environments.find { it.unitMap.containsKey(documentPath) }
    }

    private fun getUnit(documentPath: Path): CompilationUnit? {
        return getEnv(documentPath)?.let { it.unitMap[documentPath] }
    }

    private fun checkDzs(env: CompilationEnvironment) {
        if (env.generatedRoot.notExists()) {
            logger.info("Cannot find .dzs file directory of environment: {}", env)
            CLIENT.info(L10N.localize("dzs_not_found"))
        }
    }

    private fun lockForRead() {
        lock.readLock().lock()
    }

    private fun unlockForRead() {
        lock.readLock().unlock()
    }

    private fun lockForWrite() {
        lock.writeLock().lock()
    }

    private fun unlockForWrite() {
        lock.writeLock().unlock()
    }
}
