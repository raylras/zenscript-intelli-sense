package raylras.zen.dap.debugserver.handler;

import com.sun.jdi.Location;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import io.reactivex.rxjava3.disposables.Disposable;
import org.eclipse.lsp4j.debug.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.dap.DAPPositions;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.dap.debugserver.DebugSession;
import raylras.zen.dap.debugserver.ZenDebugAdapter;
import raylras.zen.dap.debugserver.breakpoint.Breakpoint;
import raylras.zen.util.Position;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SetBreakpointsHandler {

    private static final Logger logger = LoggerFactory.getLogger(ZenDebugAdapter.class);

    public static org.eclipse.lsp4j.debug.Breakpoint[] setBreakpoints(DebugAdapterContext context, SetBreakpointsArguments arguments) {
        if (context.getDebugSession() == null) {
            logger.error("Debug session not started! failed to set breakpoints");
            return new org.eclipse.lsp4j.debug.Breakpoint[0];
        }
        Path source = Path.of(arguments.getSource().getPath());
        if (!source.startsWith(context.getScriptRootPath())) {
            logger.error("The source file {} is not belong to current script root: {}", source, context.getScriptRootPath());
            return new org.eclipse.lsp4j.debug.Breakpoint[0];
        }

        String sourceName = context.getScriptRootPath().relativize(source).toString();
        List<Breakpoint> toAdds = fromDAPBreakpoints(sourceName, arguments.getBreakpoints(), context);
        List<Breakpoint> added = context.getBreakpointManager()
                .setBreakpoints(sourceName, toAdds, arguments.getSourceModified());

        for (Breakpoint breakpoint : added) {
            breakpoint.install().thenAccept(it -> {
                BreakpointEventArguments breakpointEventArguments = new BreakpointEventArguments();
                breakpointEventArguments.setReason(BreakpointEventArgumentsReason.CHANGED);
                breakpointEventArguments.setBreakpoint(toDAPBreakpoint(breakpoint, arguments.getSource(), context));
                context.getClient().breakpoint(breakpointEventArguments);
            });
        }

        return toDAPBreakpoints(added, arguments.getSource(), context);
    }

    private static List<Breakpoint> fromDAPBreakpoints(String sourceName, SourceBreakpoint[] sourceBreakpoints, DebugAdapterContext context) {
        List<Breakpoint> results = new ArrayList<>(sourceBreakpoints.length);

        for (SourceBreakpoint sourceBreakpoint : sourceBreakpoints) {
            Breakpoint breakpoint = new Breakpoint(DAPPositions.fromDAPSourceBreakpoint(sourceBreakpoint, context), sourceName, context.getDebugSession().getVM(), context.getDebugSession().eventHub());
            results.add(breakpoint);
        }

        return results;
    }

    private static org.eclipse.lsp4j.debug.Breakpoint toDAPBreakpoint(Breakpoint breakpoint, Source source, DebugAdapterContext context) {
        org.eclipse.lsp4j.debug.Breakpoint result = new org.eclipse.lsp4j.debug.Breakpoint();
        result.setId(breakpoint.getId());
        DAPPositions.fillDAPBreakpoint(result, breakpoint.getPosition(), context);
        result.setVerified(breakpoint.isVerified());
        result.setSource(source);
        return result;
    }

    private static org.eclipse.lsp4j.debug.Breakpoint[] toDAPBreakpoints(List<Breakpoint> breakpoints, Source source, DebugAdapterContext context) {
        org.eclipse.lsp4j.debug.Breakpoint[] results = new org.eclipse.lsp4j.debug.Breakpoint[breakpoints.size()];

        for (int i = 0; i < breakpoints.size(); i++) {
            results[i] = toDAPBreakpoint(breakpoints.get(i), source, context);
        }

        return results;
    }


    public static void registerBreakpointHandler(DebugAdapterContext context) {
        DebugSession debugSession = context.getDebugSession();
        if (debugSession == null) {
            return;
        }

        ClassPrepareRequest classPrepareRequest = debugSession.getVM().eventRequestManager().createClassPrepareRequest();
        if (debugSession.getVM().canUseSourceNameFilters()) {
            classPrepareRequest.addSourceNameFilter("*.zs");
        }
        classPrepareRequest.enable();

        Disposable subscribe = debugSession.eventHub()
                .breakpointEvents()
                // if there are step event at the same location of breakpoint, skip it.
                .filter(it -> it.getEventSet().size() == 1 || it.getEventSet().stream().anyMatch(t -> t instanceof StepEvent))
                .subscribe(debugEvent -> {
                    BreakpointEvent event = (BreakpointEvent) debugEvent.getEvent();
                    ThreadReference bpThread = event.thread();
                    // TODO: do not pause when eval expressions

                    StoppedEventArguments arguments = new StoppedEventArguments();
                    arguments.setText("Breakpoint");
                    arguments.setThreadId(context.getThreadManager().getThreadId(bpThread));
                    arguments.setReason(StoppedEventArgumentsReason.BREAKPOINT);
//                    arguments.setHitBreakpointIds(new Integer[]{breakpoint.getId()});
                    context.getClient().stopped(arguments);
                    debugEvent.setResume(false);
                    context.getThreadManager().threadPaused(bpThread);


                });

    }
}
