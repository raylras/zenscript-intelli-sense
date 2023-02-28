package raylras.zen.langserver;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

public class StandardIOLauncher {

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
            e.printStackTrace();
        }
    }

}
