package raylras.zen.launch;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import raylras.zen.langserver.ZenLanguageServer;

public class StandardIOLauncher {

    public static void startServer() {
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
