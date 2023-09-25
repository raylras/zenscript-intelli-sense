package raylras.zen.dap.debugserver.runtime;

import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.StepRequest;
import io.reactivex.rxjava3.disposables.Disposable;
import org.eclipse.lsp4j.debug.StoppedEventArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.dap.event.EventHub;

public class StepState {


    public enum Kind {
        STEP_IN(StepRequest.STEP_INTO),
        STEP_OUT(StepRequest.STEP_OUT),
        STEP_OVER(StepRequest.STEP_OVER);

        public final int depth;

        Kind(int depth) {
            this.depth = depth;
        }


    }

    public StepState(Kind kind, boolean isLine, Location startLocation) {
        this.kind = kind;
        this.isLine = isLine;
        if (startLocation != null) {
            this.startLine = startLocation.lineNumber();
            this.startIndex = startLocation.codeIndex();
            this.startMethod = startLocation.method();
        }
    }


    private final Kind kind;
    private final boolean isLine;

    private int startLine = 0;
    private long startIndex = -1;
    private Method startMethod = null;

    private EventRequest stepRequest;
    private Disposable eventSubscription;
    private long threadId;


    private void createStepRequest(DebugAdapterContext context, ThreadReference threadReference) {
        this.stepRequest = context.getDebugSession().getVM().eventRequestManager().createStepRequest(threadReference, isLine ? StepRequest.STEP_LINE : StepRequest.STEP_MIN, this.kind.depth);

        this.stepRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
        this.stepRequest.enable();
    }

    public void configure(DebugAdapterContext context, ThreadReference threadReference) {
        if (this.stepRequest != null && this.threadId != threadReference.uniqueID()) {
            this.stepRequest.disable();
            context.getDebugSession().getVM().eventRequestManager().deleteEventRequest(stepRequest);
            this.stepRequest = null;
        }
        if (this.stepRequest == null) {
            createStepRequest(context, threadReference);
        }
        this.threadId = threadReference.uniqueID();

    }

    public void close(DebugAdapterContext context) {
        if (this.eventSubscription != null) {
            this.eventSubscription.dispose();
            this.eventSubscription = null;
        }
        this.stepRequest.disable();
        context.getDebugSession().getVM().eventRequestManager().deleteEventRequest(stepRequest);
        context.setPendingStep(null);
    }

    public void install(DebugAdapterContext context) {

        EventHub eventHub = context.getDebugSession().eventHub();
        this.eventSubscription = eventHub.allEvents()
                .filter(it -> it.getEvent() instanceof BreakpointEvent || it.getEvent() instanceof ExceptionEvent || it.getEvent() instanceof StepEvent)
                .subscribe(event -> {

                    Event jdiEvent = event.getEvent();

                    if (!(jdiEvent instanceof StepEvent stepEvent)) {
                        // cancel step if meet a breakpoint
                        long threadId = ((LocatableEvent) jdiEvent).thread().uniqueID();
                        if (threadId == this.threadId && this.stepRequest != null) {
                            close(context);
                        }
                        return;
                    }

                    if (canStepAt(stepEvent.location()) && !isSameLocation(stepEvent.location())) {
                        StoppedEventArguments arguments = new StoppedEventArguments();
                        arguments.setThreadId(context.getThreadManager().getThreadId(stepEvent.thread()));
                        arguments.setReason(StoppedEventArgumentsReason.STEP);
                        context.getClient().stopped(arguments);
                        context.getThreadManager().threadPaused(stepEvent.thread());
                        event.setResume(false);
                        close(context);
                        return;
                    }

                    if (!isOutOfStep(stepEvent.thread())) {
                        this.configure(context, stepEvent.thread());
                    }
                    event.setResume(true);

                });
    }

    private boolean canStepAt(Location location) {
        try {
            String sourceName = location.sourceName();
            return sourceName.endsWith(".zs");
        } catch (AbsentInformationException ignored) {
            return false;
        }
    }

    private boolean isSameLocation(Location location) {
        if (location.method() != startMethod) {
            return false;
        }
        if (isLine) {
            return location.lineNumber() == startLine;
        }
        return location.codeIndex() == startIndex;
    }


    private static boolean isOutOfStep(ThreadReference threadReference) {
        try {
            return threadReference.frames().stream().noneMatch(it -> {
                Location location = it.location();
                if (location == null) {
                    return false;
                }
                if (location.lineNumber() == 0) {
                    return false;
                }
                try {
                    return location.sourceName().endsWith(".zs");
                } catch (AbsentInformationException ignored) {
                    return false;
                }
            });
        } catch (IncompatibleThreadStateException e) {
            return true;
        }
    }

}
