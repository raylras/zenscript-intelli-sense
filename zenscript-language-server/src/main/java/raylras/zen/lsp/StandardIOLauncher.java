package raylras.zen.lsp;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardIOLauncher {

    private static final Logger logger = LoggerFactory.getLogger(StandardIOLauncher.class);

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        try {
            ZenLanguageServer server = new ZenLanguageServer();
            Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, System.in, System.out);
            LanguageClient client = launcher.getRemoteProxy();
            server.connect(client);
            launcher.startListening().get();
        } catch (Exception e) {
            logger.error("Failed to start the language server", e);
        }
    }

}
