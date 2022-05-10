package raylras.zen.launch;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import raylras.zen.lsp.ZenScriptLanguageServer;
import raylras.zen.verify.Environment;

public class StandardIOLauncher {

    private Environment env = new Environment();

    public static void start() {
        new StandardIOLauncher().launchServer();
    }

    public void launchServer() {
        ZenScriptLanguageServer server = new ZenScriptLanguageServer(env);
        Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, System.in, System.out);
        server.getServices().setClient(launcher.getRemoteProxy());
        launcher.startListening();
    }

}
