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

    public void info(String pattern, Object... args) {
        logMessage(MessageType.Info, null, pattern, args);
    }

    public void warn(String pattern, Object... args) {
        logMessage(MessageType.Warning, null, pattern, args);
    }

    public void warn(Throwable thrown, String pattern, Object... args) {
        logMessage(MessageType.Warning, thrown, pattern, args);
    }

    public void error(String pattern, Object... args) {
        logMessage(MessageType.Error, null, pattern, args);
    }

    public void error(Throwable thrown, String pattern, Object... args) {
        logMessage(MessageType.Error, thrown, pattern, args);
    }

    private static Queue<MessageParams> pendingLogs;
    private static LanguageClient client;

    public static void connect(LanguageClient client) {
        Logger.client = client;
        if (pendingLogs != null) {
            for (MessageParams pendingLog : pendingLogs) {
                Logger.client.logMessage(pendingLog);
            }
        }
        pendingLogs = null;
    }

    private void logMessage(MessageType level, Throwable thrown, String pattern, Object... args) {
        MessageParams message = createMessage(level, thrown, pattern, args);
        if (client != null) {
            client.logMessage(message);
        } else {
            addToPendingQueue(message);
        }
    }

    private MessageParams createMessage(MessageType level, Throwable thrown, String pattern, Object... args) {
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

    private static void addToPendingQueue(MessageParams message) {
        if (pendingLogs == null) {
            pendingLogs = new ArrayDeque<>();
        }
        pendingLogs.add(message);
    }

}
