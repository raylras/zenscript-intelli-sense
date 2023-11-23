package raylras.zen.util;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.services.LanguageClient;
import org.slf4j.Logger;

import java.nio.file.Path;

public class LogMessages {

    public static void error(String message, Object params, Throwable t, Logger logger) {
        logger.error("{} {}", message, params, t);
    }

    public static void error(String message, Throwable t, Logger logger) {
        logger.error("{}", message, t);
    }

    public static void request(String message, Logger logger) {
        logger.info("=> {}", message);
    }

    public static void request(String message, Path path, Logger logger) {
        logger.info("=> {} {}", message, path.getFileName());
    }

    public static void request(String message, Path path, Position position, Logger logger) {
        logger.info("=> {} {}:{}:{}", message, path.getFileName(), position.getLine() + 1, position.getCharacter());
    }

    public static void response(String message, Path path, Logger logger) {
        logger.info("<= {} {}", message, path.getFileName());
    }

    public static void response(String message, Path path, Position position, Logger logger) {
        logger.info("<= {} {}:{}:{}", message, path.getFileName(), position.getLine() + 1, position.getCharacter());
    }

    public static void response(String message, Logger logger) {
        logger.info("<= {}", message);
    }

    public static void info(String message, LanguageClient client) {
        client.showMessage(new MessageParams(MessageType.Info, message));
    }

}
