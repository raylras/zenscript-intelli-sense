package raylras.zen.dap.debugserver.handler;

import com.sun.jdi.*;
import com.sun.jdi.StackFrame;
import org.eclipse.lsp4j.debug.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.dap.debugserver.runtime.StackFrameManager;
import raylras.zen.dap.debugserver.runtime.StepState;
import raylras.zen.dap.debugserver.runtime.ThreadManager;


public final class DebugJumpHandler {
    private static final Logger logger = LoggerFactory.getLogger(DebugJumpHandler.class);


    public static boolean continue_(ContinueArguments args, DebugAdapterContext context) {
        return doResume(args.getSingleThread() != null && args.getSingleThread(), args.getThreadId(), context);
    }

    public static void pause(int threadId, DebugAdapterContext context) {
        ThreadReference threadReference = context.getThreadManager().getById(threadId);
        if (threadReference == null) {
            logger.warn("Failed to pause, could not find running thread with id: {}", threadId);
            return;
        }
        boolean succeed = context.getThreadManager().pauseThread(threadReference);
        if (!succeed) {
            logger.warn("Failed to pause thread: {}, is is already paused!", threadReference.name());
        }
    }

    private static boolean doResume(boolean singleThread, Integer threadId, DebugAdapterContext context) {
        ThreadManager threadManager = context.getThreadManager();
        StackFrameManager stackFrameManager = context.getStackFrameManager();
        if (singleThread) {
            ThreadReference threadReference = context.getThreadManager().getById(threadId);
            if (threadReference == null) {
                logger.warn("Failed to resume, could not find running thread with id: {}", threadId);
                return false;
            }
            stackFrameManager.removeByThread(threadReference.uniqueID());
            boolean resumed = threadManager.resumeThread(threadReference);
            if (!resumed) {
                logger.warn("Failed to resume thread: {}", threadReference.name());
            }
            return false;
        }
        boolean allResumed = true;
        stackFrameManager.reset();
        for (ThreadReference threadReference : context.getThreadManager().pausedThreads()) {

            boolean resumed = threadManager.resumeThread(threadReference);
            if (!resumed) {
                logger.warn("Failed to resume thread: {}", threadReference.name());
                allResumed = false;
            }
        }
        return allResumed;
    }

    private static void doStep(Boolean singleThread, int threadId, DebugAdapterContext context, StepState.Kind kind, boolean isLine) {
        StepState pendingStep = context.getPendingStep();
        if (pendingStep != null) {
            pendingStep.close(context);
        }
        ThreadReference threadReference = context.getThreadManager().getById(threadId);
        Location location = null;
        try {
            StackFrame frame = threadReference.frame(0);
            location = frame.location();
        } catch (IncompatibleThreadStateException ignored) {
        }

        pendingStep = new StepState(kind, isLine, location);
        pendingStep.configure(context, threadReference);
        pendingStep.install(context);
        context.setPendingStep(pendingStep);
        doResume(singleThread != null && singleThread, threadId, context);
    }

    public static void next(NextArguments args, DebugAdapterContext context) {
        boolean isLine = args.getGranularity() != SteppingGranularity.STATEMENT;
        doStep(args.getSingleThread(), args.getThreadId(), context, StepState.Kind.STEP_OVER, isLine);
    }

    public static void stepIn(StepInArguments args, DebugAdapterContext context) {
        boolean isLine = args.getGranularity() != SteppingGranularity.STATEMENT;
        doStep(args.getSingleThread(), args.getThreadId(), context, StepState.Kind.STEP_IN, isLine);
    }

    public static void stepOut(StepOutArguments args, DebugAdapterContext context) {
        boolean isLine = args.getGranularity() != SteppingGranularity.STATEMENT;
        doStep(args.getSingleThread(), args.getThreadId(), context, StepState.Kind.STEP_OUT, isLine);
    }

    public static void goto_(GotoArguments args) {

    }


    public static void stepInTargets(StepInTargetsArguments args) {

    }

    public static void gotoTargets(GotoTargetsArguments args) {

    }

}
