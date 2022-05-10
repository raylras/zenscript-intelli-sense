package raylras.zen.launch;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import raylras.zen.lsp.ZenScriptLanguageServer;
import raylras.zen.verify.Environment;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SocketLauncher {

    public static final int DEFAULT_SOCKET_PORT = 9865;
    private Socket socket;
    private final Executor executor = Executors.newSingleThreadExecutor();

    private Environment env = new Environment();

    public static void start() {
        new SocketLauncher().launchServer();
    }

    public void launchServer() {
        CompletableFuture.runAsync(() -> System.out.println("Waiting client..."), executor)
                .thenRun(() -> {
                    try (ServerSocket serverSocket = new ServerSocket(DEFAULT_SOCKET_PORT)) {
                        socket = serverSocket.accept();
                    } catch (IOException ignore) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).thenRun(() -> {
                    System.out.println("Found a language client from " + socket.getRemoteSocketAddress() + ", starting the language server");
                    ZenScriptLanguageServer server = new ZenScriptLanguageServer(env);
                    try {
                        Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, socket.getInputStream(), socket.getOutputStream());
                        server.getServices().setClient(launcher.getRemoteProxy());
                        launcher.startListening().get();
                        if (!socket.isClosed()) { socket.close(); }
                    } catch (IOException | ExecutionException | InterruptedException ignore) {

                    }
                }).thenRun(this::launchServer);
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

}
