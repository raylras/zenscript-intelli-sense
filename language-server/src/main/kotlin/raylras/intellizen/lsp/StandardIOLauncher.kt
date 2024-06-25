package raylras.intellizen.lsp

import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageClient
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.function.Function

object StandardIOLauncher {
    val pool: ExecutorService = Executors.newCachedThreadPool()
    var client: LanguageClient? = null

    @JvmStatic
    fun main(args: Array<String>) {
        start()
    }

    fun start() {
        val server = ZenLanguageServer(ZenLanguageService())
        val launcher = LSPLauncher.createServerLauncher(server, System.`in`, System.out, pool, Function.identity())
        client = launcher.remoteProxy
        launcher.startListening().get()
    }

    fun shutdown() {
        pool.shutdown()
    }
}
