package raylras.zen.util;

import org.eclipse.lsp4j.LogTraceParams;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.services.LanguageClient;

import java.util.ArrayList;
import java.util.List;

public abstract class Logger {
    protected abstract void log(MessageType messageType, String message);

    public void info(String message, Object... args) {
        log(MessageType.Info, String.format(message, args));
    }

    public void debug(String message, Object... args) {
        log(MessageType.Log, String.format(message, args));
    }

    public void error(String message, Object... args) {
        log(MessageType.Error, String.format(message, args));
    }

    public void warn(String message, Object... args) {
        log(MessageType.Warning, String.format(message, args));
    }

    private static final List<MessageParams> pendingLogs = new ArrayList<>();
    private static LanguageClient connectedClient;

    public static void connectClient(LanguageClient client) {
        connectedClient = client;
        for (MessageParams pendingLog : pendingLogs) {
            connectedClient.logMessage(pendingLog);
        }
        pendingLogs.clear();
    }

    private static void submitLog( String message) {
        if (connectedClient == null) {
            pendingLogs.add(new MessageParams(MessageType.Log, message));
        } else {
            connectedClient.logMessage(new MessageParams(MessageType.Log, message));
        }
    }

    public static Logger getLogger(String name) {
        return new LoggerImpl(name);
    }

    private static class LoggerImpl extends Logger {
        private final String name;

        private LoggerImpl(String name) {
            this.name = name;
        }

        private String buildMessage(String level, String message) {
            return '[' + level + "] " + "[Server/" + name + "] " + message;
        }

        @Override
        protected void log(MessageType messageType, String message) {
            switch (messageType) {

                case Error:
                    Logger.submitLog( buildMessage("error", message));
                    break;
                case Warning:
                    Logger.submitLog(buildMessage("warn", message));
                    break;
                case Info:
                    Logger.submitLog(buildMessage("info", message));
                    break;
                case Log:
                    Logger.submitLog(buildMessage("debug", message));
                    break;
            }
        }
    }
}
