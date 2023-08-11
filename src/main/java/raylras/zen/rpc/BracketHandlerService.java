package raylras.zen.rpc;

import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface BracketHandlerService {

    @JsonRequest
    CompletableFuture<Map<String, String>> query(String content, boolean extras);

}