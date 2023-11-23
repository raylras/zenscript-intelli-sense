package raylras.zen.lsp;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class StandardIOLauncher {

    private static final Logger logger = LoggerFactory.getLogger(StandardIOLauncher.class);

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        try {
            ExecutorService pool = Executors.newCachedThreadPool();
            ZenLanguageServer server = new ZenLanguageServer(pool, new ZenLanguageService());
            Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, System.in, System.out, pool, Function.identity());
            LanguageClient client = launcher.getRemoteProxy();
            server.connect(client);
            launcher.startListening().get();
        } catch (Exception e) {
            logger.error("Failed to start the language server", e);
        }
    }

}
