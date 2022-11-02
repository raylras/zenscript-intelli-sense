package raylras.zen.langserver;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.services.LanguageClient;

/**
 * Log writing on stdout is disabled, we need a tool to print logs to the client through LSP.
 */
public class LanguageClientLogger {

    private LanguageClient languageClient;

    private LanguageClientLogger() {}

    public static LanguageClientLogger getInstance(LanguageServerContext serverContext) {
        LanguageClientLogger clientLogger = serverContext.get(LanguageClientLogger.class);
        if (clientLogger == null) {
            clientLogger = new LanguageClientLogger();
            serverContext.put(LanguageClientLogger.class, clientLogger);
        }
        return clientLogger;
    }

    public void connect(LanguageClient languageClient) {
        this.languageClient = languageClient;
    }

    public void logMessage(String message) {
        if (this.languageClient == null) return;
        this.languageClient.logMessage(new MessageParams(MessageType.Log, message));
    }

    public void logError(String message) {
        if (this.languageClient == null) return;
        this.languageClient.logMessage(new MessageParams(MessageType.Error, message));
    }

}
