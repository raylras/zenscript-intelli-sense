package raylras.zen.launch;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import raylras.zen.lsp.ZenScriptLanguageServer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StandardIOLauncher {

    private static final Executor executor = Executors.newSingleThreadExecutor();
    private static ZenScriptLanguageServer server;
    private static LanguageClient client;

    public static ZenScriptLanguageServer getServer() {
        return server;
    }

    public static LanguageClient getClient() {
        return client;
    }

    public static void launchServer() {
        server = new ZenScriptLanguageServer();
        Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, System.in, System.out);
        client = launcher.getRemoteProxy();
        CompletableFuture.runAsync(launcher::startListening, executor)
                .exceptionally(e -> {
                    // TODO: handle exceptions
                    e.printStackTrace();
                    return null;
                });
    }

}
