package raylras.zen.launch;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import raylras.zen.langserver.ZenLanguageServer;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class StandardIOLauncher {

    public static void startServer() {
        // Disable writing logs to stdout to avoid conflicting with client communicating over stdout with the server
        LogManager.getLogManager().reset();
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.OFF);

        try {
            ZenLanguageServer server = new ZenLanguageServer();
            Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, System.in, System.out);
            LanguageClient client = launcher.getRemoteProxy();
            server.connect(client);
            launcher.startListening().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
