package raylras.mod;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import raylras.zen.launch.SocketLauncher;
import raylras.zen.lsp.ZenScriptLanguageServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ModSideLauncher {

    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static Socket socket;
    private static ZenScriptLanguageServer server;
    private static LanguageClient client;

    public static ZenScriptLanguageServer getServer() {
        return server;
    }

    public static LanguageClient getClient() {
        return client;
    }

    // TODO: Initialize environment(ZenClasses, ZenMethods...)
    // Environment env = new Environment();

    public static void launchServer() {
        CompletableFuture.runAsync(() -> {
            System.out.println("Waiting client...");
            try {
                socket = new ServerSocket(SocketLauncher.DEFAULT_SOCKET_PORT).accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, executor).thenRun(() -> {
            System.out.println("Found a language client from " + socket.getRemoteSocketAddress() + ", starting the language server");
            server = new ZenScriptLanguageServer();
            try {
                Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, socket.getInputStream(), socket.getOutputStream());
                client = launcher.getRemoteProxy();
                launcher.startListening();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).exceptionally(e -> {
            // TODO: handle exceptions
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return null;
        });

    }


}
