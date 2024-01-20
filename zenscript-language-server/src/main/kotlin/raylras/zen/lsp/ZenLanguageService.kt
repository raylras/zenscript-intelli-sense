package raylras.zen.lsp

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.TextDocumentService
import org.eclipse.lsp4j.services.WorkspaceService
import org.slf4j.LoggerFactory
import raylras.zen.lsp.StandardIOLauncher.CLIENT
import raylras.zen.lsp.provider.*
import raylras.zen.model.*
import raylras.zen.util.*
import raylras.zen.util.l10n.L10N
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.io.path.notExists
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

class ZenLanguageService : TextDocumentService, WorkspaceService {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val environments = HashSet<CompilationEnvironment>()
    private val lock = ReentrantReadWriteLock()

    //region Text Document Service
    override fun didOpen(params: DidOpenTextDocumentParams) {
        val uri = params.textDocument.uri
        logger.start(::didOpen, uri)
        lockForWrite()
        try {
            measureTime {
                val path = uri.toPath()
                getEnv(path) ?: createEnv(path)
            }.let {
                logger.finish(::didOpen, uri, duration = it)
            }
        } catch (e: Exception) {
            logger.fail(::didOpen, uri, throwable = e)
        } finally {
            unlockForWrite()
        }
    }

    override fun didChange(params: DidChangeTextDocumentParams) {
        val uri = params.textDocument.uri
        logger.start(::didChange, uri)
        lockForWrite()
        try {
            measureTime {
                val unit = uri.toCompilationUnit()!!
                val source = params.contentChanges[0].text
                unit.load(source)
            }.let {
                logger.finish(::didChange, uri, duration = it)
            }
        } catch (e: Exception) {
            logger.fail(::didChange, uri, throwable = e)
        } finally {
            unlockForWrite()
        }
    }

    override fun didClose(params: DidCloseTextDocumentParams) {
    }

    override fun didSave(params: DidSaveTextDocumentParams) {
    }

    override fun completion(params: CompletionParams): CompletableFuture<Either<List<CompletionItem>, CompletionList>> {
        return CompletableFuture.supplyAsync {
            val uri = params.textDocument.uri
            val pos = params.position
            logger.start(::completion, uri, pos)
            lockForRead()
            try {
                measureTimedValue {
                    val unit = uri.toCompilationUnit()!!
                    CompletionProvider.completion(unit, params)
                }.let { (value, duration) ->
                    logger.finish(::completion, uri, pos, duration)
                    Either.forRight(value)
                }
            } catch (e: Exception) {
                logger.fail(::completion, uri, pos, e)
                null
            } finally {
                unlockForRead()
            }
        }
    }

    override fun resolveCompletionItem(unresolved: CompletionItem): CompletableFuture<CompletionItem> {
        return CompletableFuture.supplyAsync {
            logger.start(::resolveCompletionItem)
            try {
                measureTimedValue {
                    CompletionProvider.resolveCompletionItem(unresolved)
                }.let { (value, duration) ->
                    logger.finish(::resolveCompletionItem, duration = duration)
                    value
                }
            } catch (e: Exception) {
                logger.fail(::resolveCompletionItem, throwable = e)
                unresolved
            }
        }
    }

    override fun hover(params: HoverParams): CompletableFuture<Hover> {
        return CompletableFuture.supplyAsync {
            val uri = params.textDocument.uri
            val pos = params.position
            logger.start(::hover, uri, pos)
            lockForRead()
            try {
                measureTimedValue {
                    val unit = uri.toCompilationUnit()!!
                    HoverProvider.hover(unit, params)
                }.let { (value, duration) ->
                    logger.finish(::hover, uri, pos, duration)
                    value
                }
            } catch (e: Exception) {
                logger.fail(::hover, uri, pos, e)
                null
            } finally {
                unlockForRead()
            }
        }
    }

    override fun definition(params: DefinitionParams): CompletableFuture<Either<List<Location>, List<LocationLink>>> {
        return CompletableFuture.supplyAsync {
            val uri = params.textDocument.uri
            val pos = params.position
            logger.start(::definition, uri, pos)
            lockForRead()
            try {
                measureTimedValue {
                    val unit = uri.toCompilationUnit()!!
                    DefinitionProvider.definition(unit, params)
                }.let { (value, duration) ->
                    logger.finish(::definition, uri, pos, duration)
                    Either.forRight(value)
                }
            } catch (e: Exception) {
                logger.fail(::definition, uri, pos, e)
                null
            } finally {
                unlockForRead()
            }
        }
    }

    override fun references(params: ReferenceParams): CompletableFuture<List<Location>> {
        return CompletableFuture.supplyAsync {
            val uri = params.textDocument.uri
            val pos = params.position
            logger.start(::references, uri, pos)
            lockForRead()
            try {
                measureTimedValue {
                    val unit = uri.toCompilationUnit()!!
                    ReferencesProvider.references(unit, params)
                }.let { (value, duration) ->
                    logger.finish(::references, uri, pos, duration)
                    value
                }
            } catch (e: Exception) {
                logger.fail(::references, uri, pos, e)
                null
            } finally {
                unlockForRead()
            }
        }
    }

    override fun documentSymbol(params: DocumentSymbolParams): CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> {
        return CompletableFuture.supplyAsync {
            val uri = params.textDocument.uri
            logger.start(::documentSymbol, uri)
            lockForRead()
            try {
                measureTimedValue {
                    val unit = uri.toCompilationUnit()!!
                    DocumentSymbolProvider.documentSymbol(unit, params)
                }.let { (value, duration) ->
                    logger.finish(::documentSymbol, uri, duration = duration)
                    value.map { Either.forRight(it) }
                }
            } catch (e: Exception) {
                logger.fail(::documentSymbol, uri, throwable = e)
                null
            } finally {
                unlockForRead()
            }
        }
    }

    override fun semanticTokensFull(params: SemanticTokensParams): CompletableFuture<SemanticTokens> {
        return CompletableFuture.supplyAsync {
            val uri = params.textDocument.uri
            logger.start(::semanticTokensFull, uri)
            lockForRead()
            try {
                measureTimedValue {
                    val unit = uri.toCompilationUnit()!!
                    SemanticTokensProvider.semanticTokensFull(unit, params)
                }.let { (value, duration) ->
                    logger.finish(::semanticTokensFull, uri, duration = duration)
                    value
                }
            } catch (e: Exception) {
                logger.fail(::semanticTokensFull, uri, throwable = e)
                null
            } finally {
                unlockForRead()
            }
        }
    }

    override fun semanticTokensRange(params: SemanticTokensRangeParams): CompletableFuture<SemanticTokens> {
        return CompletableFuture.supplyAsync {
            val uri = params.textDocument.uri
            logger.start(::semanticTokensRange, uri)
            lockForRead()
            try {
                measureTimedValue {
                    val unit = uri.toCompilationUnit()!!
                    SemanticTokensProvider.semanticTokensRange(unit, params)
                }.let { (value, duration) ->
                    logger.finish(::semanticTokensRange, uri, duration = duration)
                    value
                }
            } catch (e: Exception) {
                logger.fail(::semanticTokensRange, uri, throwable = e)
                null
            } finally {
                unlockForRead()
            }
        }
    }

    override fun inlayHint(params: InlayHintParams): CompletableFuture<List<InlayHint>> {
        return CompletableFuture.supplyAsync {
            val uri = params.textDocument.uri
            logger.start(::inlayHint, uri)
            lockForRead()
            try {
                measureTimedValue {
                    val unit = uri.toCompilationUnit()!!
                    InlayHintProvider.inlayHint(unit, params)
                }.let { (value, duration) ->
                    logger.finish(::inlayHint, uri, duration = duration)
                    value
                }
            } catch (e: Exception) {
                logger.fail(::inlayHint, uri, throwable = e)
                null
            } finally {
                unlockForRead()
            }
        }
    }
    //endregion

    //region Workspace Service
    override fun didChangeConfiguration(params: DidChangeConfigurationParams) {
    }

    override fun didChangeWatchedFiles(params: DidChangeWatchedFilesParams) {
        logger.start(::didChangeWatchedFiles)
        lockForWrite()
        try {
            measureTime {
                for (event in params.changes) {
                    val path = event.uri.toPath()
                    getEnv(path)?.let { env ->
                        when (event.type) {
                            FileChangeType.Created -> {
                                createUnit(path, env).load()
                            }

                            FileChangeType.Changed -> {
                                env.unitMap[path]!!.load()
                            }

                            FileChangeType.Deleted -> env.unitMap.remove(path)

                            else -> {}
                        }
                    }
                }
            }.let {
                logger.finish(::didChangeWatchedFiles, duration = it)
            }
        } catch (e: Exception) {
            logger.fail(::didChangeWatchedFiles, throwable = e)
        } finally {
            unlockForWrite()
        }
    }

    override fun didChangeWorkspaceFolders(params: DidChangeWorkspaceFoldersParams) {
    }
    //endregion

    //region Private
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

    private fun String.toCompilationUnit(): CompilationUnit? {
        return getUnit(this.toPath())
    }

    private fun String.toPath(): Path {
        return toPath(this)
    }
    //endregion
}
