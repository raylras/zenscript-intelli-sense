package raylras.zen.util;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import raylras.zen.lsp.Manager;
import stanhebben.zenscript.IZenErrorLogger;
import stanhebben.zenscript.util.ZenPosition;

public class CommonErrorLogger implements IZenErrorLogger {

    @Override
    public void error(String message) {
        System.out.println(message);
        Manager.getClient().logMessage(new MessageParams(MessageType.Error, message));
    }

    @Override
    public void error(String message, Throwable e) {
        System.out.println(message);
        Manager.getClient().logMessage(new MessageParams(MessageType.Error, message + e));
    }

    @Override
    public void warning(String message) {
        System.out.println(message);
        Manager.getClient().logMessage(new MessageParams(MessageType.Warning, message));
    }

    @Override
    public void info(String message) {
        System.out.println(message);
        Manager.getClient().logMessage(new MessageParams(MessageType.Info, message));
    }

    @Override
    public void error(ZenPosition position, String message) {
        if (position == null) {
            System.out.println(message);
            Manager.getClient().logMessage(new MessageParams(MessageType.Error, message));
        } else {
            System.out.println(position + ": " + message);
            Manager.getClient().logMessage(new MessageParams(MessageType.Error, position+ ": " + message));
        }
    }

    @Override
    public void warning(ZenPosition position, String message) {
        if (position == null) {
            System.out.println(message);
            Manager.getClient().logMessage(new MessageParams(MessageType.Warning, message));
        } else {
            System.out.println(position + ": " + message);
            Manager.getClient().logMessage(new MessageParams(MessageType.Warning, position+ ": " + message));
        }
    }

    @Override
    public void info(ZenPosition position, String message) {
        if (position == null) {
            System.out.println(message);
            Manager.getClient().logMessage(new MessageParams(MessageType.Info, message));
        } else {
            System.out.println(position + ": " + message);
            Manager.getClient().logMessage(new MessageParams(MessageType.Info, position+ ": " + message));
        }
    }

}
