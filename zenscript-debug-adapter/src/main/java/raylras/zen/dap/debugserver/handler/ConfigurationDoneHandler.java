package raylras.zen.dap.debugserver.handler;

import com.sun.jdi.event.*;
import io.reactivex.rxjava3.disposables.Disposable;
import org.eclipse.lsp4j.debug.ExitedEventArguments;
import org.eclipse.lsp4j.debug.TerminatedEventArguments;
import org.eclipse.lsp4j.debug.ThreadEventArguments;
import org.eclipse.lsp4j.debug.ThreadEventArgumentsReason;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.dap.debugserver.DebugSession;

public class ConfigurationDoneHandler {


    public static void handle(DebugAdapterContext context) {
        DebugSession session = context.getDebugSession();

        if (session == null) {
            // the vm is not started
            TerminatedEventArguments terminatedEventArguments = new TerminatedEventArguments();
            terminatedEventArguments.setRestart(false);
            context.getClient().terminated(terminatedEventArguments);
            return;
        }
        Disposable subscribe = session.eventHub().allEvents().subscribe(jdiEvent -> {
            Event event = jdiEvent.getEvent();

            if (event instanceof VMStartEvent vmStartEvent) {
                handleVMStart(vmStartEvent);
            } else if (event instanceof VMDeathEvent vmDeathEvent) {
                handleVMDeath(vmDeathEvent, context);
            } else if (event instanceof VMDisconnectEvent vmDisconnectEvent) {
                handleVMDisconnect(vmDisconnectEvent, context);
            } else if (event instanceof ThreadStartEvent threadStartEvent) {
                handleThreadStart(threadStartEvent, context);
            } else if (event instanceof ThreadDeathEvent threadDeathEvent) {
                handleThreadDeath(threadDeathEvent, context);
            } else if (event instanceof ExceptionEvent exceptionEvent) {
                handleException(exceptionEvent);
            }

        });


    }

    private static void handleException(ExceptionEvent exceptionEvent) {
    }

    private static void handleThreadDeath(ThreadDeathEvent threadDeathEvent, DebugAdapterContext context) {
        context.getStackFrameManager().removeByThread(threadDeathEvent.thread().uniqueID());
        context.getDebugObjectManager().removeByThread(threadDeathEvent.thread().uniqueID());
        int id = context.getThreadManager().threadStopped(threadDeathEvent.thread());
        ThreadEventArguments threadEventArguments = new ThreadEventArguments();
        threadEventArguments.setThreadId(id);
        threadEventArguments.setReason(ThreadEventArgumentsReason.EXITED);
        context.getClient().thread(threadEventArguments);
    }

    private static void handleThreadStart(ThreadStartEvent threadStartEvent, DebugAdapterContext context) {
        int id = context.getThreadManager().threadStarted(threadStartEvent.thread());
        ThreadEventArguments threadEventArguments = new ThreadEventArguments();
        threadEventArguments.setThreadId(id);
        threadEventArguments.setReason(ThreadEventArgumentsReason.STARTED);
        context.getClient().thread(threadEventArguments);
    }

    private static void handleVMDisconnect(VMDisconnectEvent vmDisconnectEvent, DebugAdapterContext context) {

        TerminatedEventArguments terminatedEventArguments = new TerminatedEventArguments();
        terminatedEventArguments.setRestart(false);
        context.getClient().terminated(terminatedEventArguments);
    }

    private static void handleVMDeath(VMDeathEvent vmDeathEvent, DebugAdapterContext context) {
        ExitedEventArguments exitedEventArguments = new ExitedEventArguments();
        try {
            exitedEventArguments.setExitCode(vmDeathEvent.virtualMachine().process().exitValue());
        } catch (Exception ignored) {

        }
        context.getClient().exited(exitedEventArguments);
    }

    private static void handleVMStart(VMStartEvent vmStartEvent) {
    }


}
