package raylras.zen.lsp

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.services.LanguageServer
import org.slf4j.LoggerFactory
import raylras.zen.lsp.provider.data.SEMANTIC_TOKENS_LEGEND
import raylras.zen.model.ZS_FILE_EXTENSION
import raylras.zen.util.l10n.L10N
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.system.exitProcess

private val logger = LoggerFactory.getLogger(ZenLanguageServer::class.java)

class ZenLanguageServer(private val service: ZenLanguageService) : LanguageServer {
    override fun initialize(params: InitializeParams): CompletableFuture<InitializeResult> {
        L10N.setLocale(params.locale)
        val capabilities = ServerCapabilities().apply {
            setDocumentSymbolProvider(true)
//            setWorkspaceSymbolProvider(true)
//            setDocumentHighlightProvider(true)
            setReferencesProvider(true)
//            setDeclarationProvider(true)
            setDefinitionProvider(true)
//            setTypeDefinitionProvider(true)
            setHoverProvider(true)
//            setRenameProvider(true)
            setTextDocumentSync(TextDocumentSyncKind.Full)
            completionProvider = CompletionOptions(true, listOf(".", "<"))
//            signatureHelpProvider = SignatureHelpOptions(listOf("(", ","))
            semanticTokensProvider = SemanticTokensWithRegistrationOptions(SEMANTIC_TOKENS_LEGEND, false, true)
            setInlayHintProvider(true)
        }
        return CompletableFuture.completedFuture(InitializeResult(capabilities))
    }

    override fun initialized(params: InitializedParams) {
        startListeningFileChanges()
        logger.info("Language server initialized")
    }

    override fun shutdown(): CompletableFuture<Any> {
        return CompletableFuture.supplyAsync {
            logger.info("Language server shutting down")
            StandardIOLauncher.shutdown()
        }
    }

    override fun exit() {
        logger.info("Language server exiting")
        exitProcess(0)
    }

    override fun getTextDocumentService() = service

    override fun getWorkspaceService() = service

    override fun setTrace(params: SetTraceParams) {
    }

    private fun startListeningFileChanges() {
        val watcher = listOf(
            FileSystemWatcher(
                Either.forLeft("**/*$ZS_FILE_EXTENSION"),
                WatchKind.Create + WatchKind.Change + WatchKind.Delete
            )
        )
        val options = DidChangeWatchedFilesRegistrationOptions(watcher)
        val registration = Registration(UUID.randomUUID().toString(), "workspace/didChangeWatchedFiles", options)
        StandardIOLauncher.CLIENT?.registerCapability(RegistrationParams(listOf(registration)))
    }
}
