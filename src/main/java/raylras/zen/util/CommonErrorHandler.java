package raylras.zen.util;

import org.eclipse.lsp4j.*;
import raylras.zen.Main;
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
        Main.getClient().logMessage(new MessageParams(MessageType.Error, message));
    }

    @Override
    public void error(String message, Throwable e) {
        System.out.println(message);
        Main.getClient().logMessage(new MessageParams(MessageType.Error, message + e));
    }

    @Override
    public void warning(String message) {
        System.out.println(message);
        Main.getClient().logMessage(new MessageParams(MessageType.Warning, message));
    }

    @Override
    public void info(String message) {
        System.out.println(message);
        Main.getClient().logMessage(new MessageParams(MessageType.Info, message));
    }

    @Override
    public void error(ZenPosition zenPos, String message) {
        System.out.println(zenPos + ": " + message);
        Main.getClient().logMessage(new MessageParams(MessageType.Error, zenPos+ ": " + message));
        Position pos = PosUtil.convert(zenPos);
        //diagnostics.add(new Diagnostic(new Range(pos,pos), message));
    }

    @Override
    public void warning(ZenPosition zenPos, String message) {
        System.out.println(zenPos + ": " + message);
        Main.getClient().logMessage(new MessageParams(MessageType.Warning, zenPos+ ": " + message));
        Position pos = PosUtil.convert(zenPos);
        //diagnostics.add(new Diagnostic(new Range(pos,pos), message));
    }

    @Override
    public void info(ZenPosition zenPos, String message) {
        System.out.println(zenPos + ": " + message);
        Main.getClient().logMessage(new MessageParams(MessageType.Info, zenPos+ ": " + message));
        Position pos = PosUtil.convert(zenPos);
        //diagnostics.add(new Diagnostic(new Range(pos,pos), message));
    }

    @Override
    public void error(ZenPosition zenPosStart, ZenPosition zenPosEnd, String message) {
        error(zenPosStart, message);
        Position posStart = PosUtil.convert(zenPosStart);
        Position posEnd = PosUtil.convert(zenPosEnd);
        diagnostics.add(new Diagnostic(new Range(posStart, posEnd), message));
    }

    @Override
    public void warning(ZenPosition zenPosStart, ZenPosition zenPosEnd, String message) {
        warning(zenPosStart, message);
        Position posStart = PosUtil.convert(zenPosStart);
        Position posEnd = PosUtil.convert(zenPosEnd);
        diagnostics.add(new Diagnostic(new Range(posStart, posEnd), message));
    }

    @Override
    public void info(ZenPosition zenPosStart, ZenPosition zenPosEnd, String message) {
        info(zenPosStart, message);
        Position posStart = PosUtil.convert(zenPosStart);
        Position posEnd = PosUtil.convert(zenPosEnd);
        diagnostics.add(new Diagnostic(new Range(posStart, posEnd), message));
    }

}
