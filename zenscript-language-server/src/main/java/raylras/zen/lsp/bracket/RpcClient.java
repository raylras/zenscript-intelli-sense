package raylras.zen.lsp.bracket;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.lsp.StandardIOLauncher;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    static Map<String, List<String>> getEntryPropertiesRemote(String validExpr)
            throws IOException, ExecutionException, InterruptedException {
        Map<String, Object> properties = getRemoteService().query(validExpr, true)
                .exceptionally(e -> {
                    invalidateRemoteService();
                    throw new RuntimeException(e);
                }).get();
        return properties.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    if (entry.getValue() instanceof String str) {
                        return List.of(str);
                    } else if (entry.getValue() instanceof List<?> list) {
                        return list.stream().map(Object::toString).toList();
                    } else {
                        throw new RuntimeException("Unexpected type of value: " + entry.getValue());
                    }
                }));
    }

    private static Socket socket;
    private static RemoteService remoteService;

    private static RemoteService getRemoteService() throws IOException {
        if (remoteService != null) {
            return remoteService;
        } else {
            return createRemoteService();
        }
    }

    private static RemoteService createRemoteService() throws IOException {
        socket = new Socket("127.0.0.1", 6489);
        Launcher<RemoteService> launcher = Launcher.createLauncher(new Object(), RemoteService.class, socket.getInputStream(), socket.getOutputStream(), StandardIOLauncher.POOL, Function.identity());
        launcher.startListening();
        remoteService = launcher.getRemoteProxy();
        return remoteService;
    }

    private static void invalidateRemoteService() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("Failed to close socket: {}", socket, e);
            } finally {
                socket = null;
            }
        }
        remoteService = null;
    }

    private interface RemoteService {
        @JsonRequest
        CompletableFuture<Map<String, Object>> query(String validExpr, boolean extras);
    }

}
