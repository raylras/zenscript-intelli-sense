package raylras.zen.launch;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import raylras.zen.ls.ZenScriptLanguageServer;

import java.util.concurrent.ExecutionException;

public class StandardIOLauncher {

    public void launchServer() {
        try {
            ZenScriptLanguageServer server = new ZenScriptLanguageServer();
            Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, System.in, System.out);
            server.getServices().setClient(launcher.getRemoteProxy());
            launcher.startListening().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
