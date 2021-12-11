package raylras.zen.util;

import org.eclipse.lsp4j.*;
import raylras.zen.lsp.Manager;
import stanhebben.zenscript.IZenErrorLogger;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

public class CommonErrorHandler implements IZenErrorLogger {

    List<Diagnostic> diagnostics;

    public CommonErrorHandler(List<Diagnostic> diagnostics) {
        this.diagnostics = diagnostics;
    }

    public List<Diagnostic> getDiagnostics() {
        return diagnostics;
    }

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
        System.out.println(position + ": " + message);
        Manager.getClient().logMessage(new MessageParams(MessageType.Error, position+ ": " + message));
        Position pos = new Position(position.getLine() - 1, position.getLineOffset());
        //diagnostics.add(new Diagnostic(new Range(pos,pos), message));
    }

    @Override
    public void warning(ZenPosition position, String message) {
        System.out.println(position + ": " + message);
        Manager.getClient().logMessage(new MessageParams(MessageType.Warning, position+ ": " + message));
        Position pos = new Position(position.getLine() - 1, position.getLineOffset());
        //diagnostics.add(new Diagnostic(new Range(pos,pos), message));
    }

    @Override
    public void info(ZenPosition position, String message) {
        System.out.println(position + ": " + message);
        Manager.getClient().logMessage(new MessageParams(MessageType.Info, position+ ": " + message));
        Position pos = new Position(position.getLine() - 1, position.getLineOffset());
        //diagnostics.add(new Diagnostic(new Range(pos,pos), message));
    }

    @Override
    public void error(ZenPosition start, ZenPosition end, String message) {
        error(start, message);
        Position posStart = new Position(start.getLine() - 1, start.getLineOffset() - 1);
        Position posEnd = new Position(end.getLine() - 1, end.getLineOffset());
        diagnostics.add(new Diagnostic(new Range(posStart, posEnd), message));
    }

    @Override
    public void warning(ZenPosition start, ZenPosition end, String message) {
        warning(start, message);
        Position posStart = new Position(start.getLine() - 1, start.getLineOffset() - 1);
        Position posEnd = new Position(end.getLine() - 1, end.getLineOffset());
        diagnostics.add(new Diagnostic(new Range(posStart, posEnd), message));
    }

    @Override
    public void info(ZenPosition start, ZenPosition end, String message) {
        info(start, message);
        Position posStart = new Position(start.getLine() - 1, start.getLineOffset() - 1);
        Position posEnd = new Position(end.getLine() - 1, end.getLineOffset());
        diagnostics.add(new Diagnostic(new Range(posStart, posEnd), message));
    }

}
