package raylras.intellizen.languageserver

import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageClient
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.function.Function

object StandardIOLauncher {
    val POOL: ExecutorService = Executors.newCachedThreadPool()
    var CLIENT: LanguageClient? = null

    @JvmStatic
    fun main(args: Array<String>) {
        start()
    }

    fun start() {
        val server = ZenLanguageServer(ZenLanguageService())
        val launcher = LSPLauncher.createServerLauncher(server, System.`in`, System.out, POOL, Function.identity())
        CLIENT = launcher.remoteProxy
        launcher.startListening().get()
    }

    fun shutdown() {
        POOL.shutdown()
    }
}
