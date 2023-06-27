package raylras.zen.util;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.services.LanguageClient;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.Queue;

public class Logger {

    private final String name;

    public Logger(String name) {
        this.name = name;
    }

    public static Logger getLogger(String name) {
        return new Logger(name);
    }

    public void logInfo(String pattern, Object... args) {
        logMessage(MessageType.Info, null, pattern, args);
    }

    public void logWarn(String pattern, Object... args) {
        logMessage(MessageType.Warning, null, pattern, args);
    }

    public void logWarn(Throwable thrown, String pattern, Object... args) {
        logMessage(MessageType.Warning, thrown, pattern, args);
    }

    public void logError(String pattern, Object... args) {
        logMessage(MessageType.Error, null, pattern, args);
    }

    public void logError(Throwable thrown, String pattern, Object... args) {
        logMessage(MessageType.Error, thrown, pattern, args);
    }

    public void showInfo(String pattern, Object... args) {
        showMessage(MessageType.Info, pattern, args);
    }

    public void showWarn(String pattern, Object... args) {
        showMessage(MessageType.Warning, pattern, args);
    }

    public void showError(String pattern, Object... args) {
        showMessage(MessageType.Error, pattern, args);
    }

    private static Queue<MessageParams> pendingLogMessages;
    private static Queue<MessageParams> pendingShowMessages;
    private static LanguageClient client;

    public static void connect(LanguageClient client) {
        Logger.client = client;
        if (pendingLogMessages != null) {
            for (MessageParams pendingLog : pendingLogMessages) {
                Logger.client.logMessage(pendingLog);
            }
            pendingLogMessages = null;
        }
        if (pendingShowMessages != null) {
            for (MessageParams pendingShow : pendingShowMessages) {
                Logger.client.showMessage(pendingShow);
            }
            pendingShowMessages = null;
        }
    }

    private void logMessage(MessageType level, Throwable thrown, String pattern, Object... args) {
        MessageParams message = createLogMessage(level, thrown, pattern, args);
        if (client != null) {
            client.logMessage(message);
        } else {
            addToPendingLogMessages(message);
        }
    }

    private MessageParams createLogMessage(MessageType level, Throwable thrown, String pattern, Object... args) {
        String formatted = "[" + name + "] " + MessageFormat.format(pattern, args);
        if (thrown == null) {
            return new MessageParams(level, formatted);
        } else {
            StringWriter buffer = new StringWriter();
            PrintWriter printer = new PrintWriter(buffer, true);
            printer.println(formatted);
            thrown.printStackTrace(printer);
            return new MessageParams(level, buffer.toString());
        }
    }

    private static void addToPendingLogMessages(MessageParams message) {
        if (pendingLogMessages == null) {
            pendingLogMessages = new ArrayDeque<>();
        }
        pendingLogMessages.add(message);
    }

    private void showMessage(MessageType level, String pattern, Object... args) {
        MessageParams message = createShowMessage(level, pattern, args);
        if (client!= null) {
            client.showMessage(message);
        } else {
            addToPendingLogMessages(message);
        }
    }

    private MessageParams createShowMessage(MessageType level, String pattern, Object... args) {
        String formatted = MessageFormat.format(pattern, args);
        return new MessageParams(level, formatted);
    }

    private static void addToPendingShowMessages(MessageParams message) {
        if (pendingShowMessages == null) {
            pendingShowMessages = new ArrayDeque<>();
        }
        pendingShowMessages.add(message);
    }

}
