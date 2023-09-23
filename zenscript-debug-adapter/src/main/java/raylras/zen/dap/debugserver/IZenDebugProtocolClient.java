package raylras.zen.dap.debugserver;

import org.eclipse.lsp4j.debug.ContinuedEventArguments;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;

public interface IZenDebugProtocolClient extends IDebugProtocolClient {
    @JsonNotification
    default void outputLog(String args) {
    }
}
