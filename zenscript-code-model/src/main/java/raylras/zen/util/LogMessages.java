package raylras.zen.util;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.services.LanguageClient;
import org.slf4j.Logger;

import java.nio.file.Path;

public class LogMessages {

    private static final ThreadLocal<Long> START_TIME = ThreadLocal.withInitial(System::currentTimeMillis);

    public static void info(String message, LanguageClient client) {
        client.showMessage(new MessageParams(MessageType.Info, message));
    }

    public static void error(String label, Object params, Throwable t, Logger logger) {
        logger.error("{} {}", label, params, t);
    }

    public static void error(String label, Throwable t, Logger logger) {
        logger.error("{}", label, t);
    }

    public static void start(String label, Logger logger) {
        START_TIME.set(System.currentTimeMillis());
        logger.info("{} started", label);
    }

    public static void start(String label, Path path, Logger logger) {
        START_TIME.set(System.currentTimeMillis());
        logger.info("{} {} started", label, path.getFileName());
    }

    public static void start(String label, Path path, Position position, Logger logger) {
        START_TIME.set(System.currentTimeMillis());
        logger.info("{} {}:{}:{} started", label, path.getFileName(), position.getLine() + 1, position.getCharacter());
    }

    public static void finish(String label, Path path, Logger logger) {
        logger.info("{} {} finished {} ms", label, path.getFileName(), getElapsedMillis());
    }

    public static void finish(String label, Path path, Position position, Logger logger) {
        logger.info("{} {}:{}:{} finished {} ms", label, path.getFileName(), position.getLine() + 1, position.getCharacter(), getElapsedMillis());
    }

    public static void finish(String label, Logger logger) {
        logger.info("{} finished {} ms", label, getElapsedMillis());
    }

    private static long getElapsedMillis() {
        return System.currentTimeMillis() - START_TIME.get();
    }

}
