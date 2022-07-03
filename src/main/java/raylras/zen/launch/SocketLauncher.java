package raylras.zen.launch;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import raylras.zen.ls.ZenScriptLanguageServer;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketLauncher {

    public static final int DEFAULT_SOCKET_PORT = 9865;

    public void launchServer(final int port) {
        while (true) {
            System.out.println("Launching server via socket on port " + port);
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Waiting language client...");
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Found a language client from " + socket.getRemoteSocketAddress() + ", starting the language server");
                    ZenScriptLanguageServer server = new ZenScriptLanguageServer();
                    Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, socket.getInputStream(), socket.getOutputStream());
                    server.getServices().setClient(launcher.getRemoteProxy());
                    launcher.startListening().get();
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

}
