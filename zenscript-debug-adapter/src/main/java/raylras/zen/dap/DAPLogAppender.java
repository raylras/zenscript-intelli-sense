package raylras.zen.dap;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import org.eclipse.lsp4j.debug.OutputEventArguments;
import org.eclipse.lsp4j.debug.OutputEventArgumentsCategory;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import raylras.zen.dap.debugserver.IZenDebugProtocolClient;

import java.lang.ref.WeakReference;

public class DAPLogAppender extends AppenderBase<ILoggingEvent> {
    private final WeakReference<IZenDebugProtocolClient> client;

    private Layout<ILoggingEvent> layout;

    public void setLayout(Layout<ILoggingEvent> layout) {
        this.layout = layout;
    }

    public DAPLogAppender(IZenDebugProtocolClient client) {
        this.client = new WeakReference<>(client);
    }

    @Override
    protected void append(ILoggingEvent event) {
        IZenDebugProtocolClient client = this.client.get();
        if(layout != null && client != null) {
            String s = layout.doLayout(event);
            client.outputLog(s);


        }
    }


}