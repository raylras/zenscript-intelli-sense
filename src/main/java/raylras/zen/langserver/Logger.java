package raylras.zen.langserver;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.services.LanguageClient;

/**
 *  Class used to log messages to language client.
 */
public class Logger {

    public LanguageClient client;

    public void info(String message) {
        logMessage(message, MessageType.Info);
    }

    public void warning(String message) {
        logMessage(message, MessageType.Warning);
    }

    public void error(String message) {
        logMessage(message, MessageType.Error);
    }

    private void logMessage(String message, MessageType type) {
        if (client == null) return;
        client.logMessage(new MessageParams(type, message));
    }

}
