package raylras.zen.dap.debugserver.handler;

import com.sun.jdi.ThreadReference;
import org.eclipse.lsp4j.debug.*;
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.dap.debugserver.runtime.StackFrameManager;
import raylras.zen.dap.debugserver.runtime.ThreadManager;


public final class DebugJumpHandler {
    private static final Logger logger = LoggerFactory.getLogger(DebugJumpHandler.class);


    public static boolean continue_(ContinueArguments args, DebugAdapterContext context) {
        ThreadManager threadManager = context.getThreadManager();
        StackFrameManager stackFrameManager = context.getStackFrameManager();
        if (args.getSingleThread() != null && args.getSingleThread()) {
            ThreadReference threadReference = context.getThreadManager().getById(args.getThreadId());
            if (threadReference == null) {
                logger.warn("Failed to resume, could not find running thread with id: {}", args.getThreadId());
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

    public static void next(NextArguments args) {

    }

    public static void stepIn(StepInArguments args) {

    }

    public static void stepOut(StepOutArguments args) {

    }

    public static void goto_(GotoArguments args) {

    }


    public static void stepInTargets(StepInTargetsArguments args) {

    }

    public static void gotoTargets(GotoTargetsArguments args) {

    }

}
