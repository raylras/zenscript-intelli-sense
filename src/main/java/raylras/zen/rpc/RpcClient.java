package raylras.zen.rpc;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

public class RpcClient {

    public static Map<String, String> queryBracketHandler(String raw) {
        try {
            BracketHandlerService service = RpcClient.getBracketHandlerService();
            CompletableFuture<Map<String, String>> future = service.query(raw, true);
            future.exceptionally(e -> {
                logger.error("Failed to query bracket handler: {}", raw, e);
                try {
                    RpcClient.socket.close();
                } catch (IOException ex) {
                    logger.error("Failed to close socket: {}", socket, e);
                }
                RpcClient.bracketHandlerService = null;
                return Collections.emptyMap();
            });
            return future.get();
        } catch (Exception e) {
            logger.error("Failed to query bracket handler: {}", raw, e);
            return Collections.emptyMap();
        }
    }

    public static BracketHandlerService getBracketHandlerService() throws IOException {
        if (bracketHandlerService == null) {
            socket = new Socket("127.0.0.1", 6489);
            Launcher<BracketHandlerService> launcher = Launcher.createLauncher(new Object(), BracketHandlerService.class, socket.getInputStream(), socket.getOutputStream(), executorService, Function.identity());
            launcher.startListening();
            bracketHandlerService = launcher.getRemoteProxy();
        }
        return bracketHandlerService;
    }

    public static void shutdown() {
        executorService.shutdown();
    }

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Socket socket;
    private static BracketHandlerService bracketHandlerService;

}
