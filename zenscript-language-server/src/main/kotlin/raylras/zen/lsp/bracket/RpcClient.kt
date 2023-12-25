package raylras.zen.lsp.bracket

import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest
import raylras.zen.lsp.StandardIOLauncher.POOL
import java.net.Socket
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.function.Function

object RpcClient {
    fun getEntryPropertiesRemote(validExpr: String): Map<String, Any> {
        return getRemoteService()
            .query(validExpr, true)
            .exceptionally { e ->
                invalidateRemoteService()
                throw e
            }
            .get(1, TimeUnit.SECONDS)
    }

    private var socket: Socket? = null
    private var remoteService: RemoteService? = null

    private fun getRemoteService(): RemoteService {
        if (remoteService == null) {
            createRemoteService()
        }
        return remoteService!!
    }

    private fun createRemoteService() {
        socket = Socket("127.0.0.1", 6489)
        val launcher = Launcher.createLauncher(
            Any(),
            RemoteService::class.java,
            socket!!.getInputStream(),
            socket!!.getOutputStream(),
            POOL,
            Function.identity()
        )
        launcher.startListening()
        remoteService = launcher.remoteProxy
    }

    private fun invalidateRemoteService() {
        socket?.close()
        socket = null
        remoteService = null
    }

    private interface RemoteService {
        @JsonRequest
        fun query(validExpr: String?, extras: Boolean): CompletableFuture<Map<String, Any>>
    }
}
