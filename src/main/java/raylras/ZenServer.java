package raylras;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import raylras.lsp.ZenScriptLanguageServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Mod(modid = ZenServer.MODID, name = ZenServer.NAME, version = ZenServer.VERSION, dependencies = ZenServer.DEPENDENCIES)
public class ZenServer {

    public static final String MODID = "zenserver";
    public static final String NAME = "ZenServer";
    public static final String VERSION = "1.0";
    public static final String DEPENDENCIES = "required-after:crafttweaker";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static ZenScriptLanguageServer langServer;
    public static final int PORT = 9865;
    private static Socket socket;

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent e) {
        new Thread(() -> {
            try {
                socket = new ServerSocket(PORT).accept();
                LOGGER.info("Found a language client from {}, starting the language server", socket.getRemoteSocketAddress());
                langServer = new ZenScriptLanguageServer();
                Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(langServer, socket.getInputStream(), socket.getOutputStream());
                langServer.connect(launcher.getRemoteProxy());
                launcher.startListening();
            } catch (IOException ex) {
                LOGGER.error("Could not start the language server", ex);
            }
        }).start();

    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent e) {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
