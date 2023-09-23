package raylras.zen.dap.debugserver.handler;

import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadReference;
import org.eclipse.lsp4j.debug.StackTraceArguments;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import raylras.zen.dap.debugserver.DebugAdapterContext;

import java.util.ArrayList;
import java.util.List;

public final class StackTraceHandler {


    public static StackTraceResponse getStackTrace(DebugAdapterContext context, StackTraceArguments args) throws IncompatibleThreadStateException {

        StackTraceResponse stackTraceResponse = new StackTraceResponse();
        ThreadReference runningThread = context.getThreadManager().getById(args.getThreadId());

        int start = 0;
        if (args.getStartFrame() != null) {
            start = args.getStartFrame();
        }

        if (start < 0) {
            stackTraceResponse.setTotalFrames(0);
            stackTraceResponse.setStackFrames(new org.eclipse.lsp4j.debug.StackFrame[0]);
            return stackTraceResponse;
        }

        int end = runningThread.frameCount();
        if (args.getLevels() != null && args.getLevels() > 0) {
            end = Math.min(args.getLevels() + start, end);
        }

        List<org.eclipse.lsp4j.debug.StackFrame> stackFrames = new ArrayList<>(end - start);
        for (int i = start; i < end; i++) {
            StackFrame frame = runningThread.frame(i);

            org.eclipse.lsp4j.debug.StackFrame dapFrame = context.getStackFrameManager().toDAPStackFrame(frame, context);
            stackFrames.add(dapFrame);
        }

        stackTraceResponse.setStackFrames(stackFrames.toArray(org.eclipse.lsp4j.debug.StackFrame[]::new));
        stackTraceResponse.setTotalFrames(runningThread.frameCount());

        return stackTraceResponse;
    }


}
