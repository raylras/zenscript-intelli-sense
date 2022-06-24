package raylras.zen.launch;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import raylras.zen.lsp.ZenScriptLanguageServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SocketLauncher implements ServerLauncher {

    public static final int DEFAULT_SOCKET_PORT = 9865;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void launchServer() {
        System.out.println("Waiting language client...");
        CompletableFuture.supplyAsync(() -> {
                    while (true) {
                        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_SOCKET_PORT)) {
                            return serverSocket.accept();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Thread.yield();
                        }
                    }
                }, executor)
                .thenApply(socket -> {
                    System.out.println("Found a language client from " + socket.getRemoteSocketAddress() + ", starting the language server");
                    ZenScriptLanguageServer server = new ZenScriptLanguageServer();
                    try(socket) {
                        Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, socket.getInputStream(), socket.getOutputStream());
                        server.getServices().setClient(launcher.getRemoteProxy());
                        launcher.startListening().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .thenRunAsync(this::launchServer, executor);
    }

}
