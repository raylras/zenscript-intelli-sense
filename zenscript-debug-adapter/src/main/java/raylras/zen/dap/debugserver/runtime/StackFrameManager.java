package raylras.zen.dap.debugserver.runtime;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.StackFrame;
import com.sun.jdi.request.BreakpointRequest;
import org.eclipse.lsp4j.debug.Source;
import org.eclipse.lsp4j.debug.SourcePresentationHint;
import org.eclipse.lsp4j.debug.StackFramePresentationHint;
import raylras.zen.dap.DAPPositions;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.dap.debugserver.breakpoint.Breakpoint;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StackFrameManager {

    private final ObjectIdMap<StackFrame> stackFrameIds = new ObjectIdMap<>();
    private final Map<Long, Set<Integer>> stackFramesByThread = new ConcurrentHashMap<>();


    public void reset() {
        stackFrameIds.reset();
        stackFramesByThread.clear();
    }

    public void removeByThread(long threadUUID) {
        Set<Integer> toRemove = stackFramesByThread.remove(threadUUID);
        if (toRemove == null || toRemove.isEmpty()) {
            return;
        }
        if (stackFramesByThread.isEmpty()) {
            reset();
            return;
        }
        for (Integer id : toRemove) {
            stackFrameIds.removeById(id);
        }

    }

    public int stackFrameId(StackFrame stackFrame) {
        int id = stackFrameIds.getOrPut(stackFrame);

        if (id >= 0) {
            stackFramesByThread.computeIfAbsent(stackFrame.thread().uniqueID(), it -> new HashSet<>()).add(id);
        }

        return id;
    }

    public StackFrame getById(int id) {
        return stackFrameIds.getById(id);
    }


    public org.eclipse.lsp4j.debug.StackFrame toDAPStackFrame(StackFrame jdiFrame, DebugAdapterContext context) {

        int id = stackFrameId(jdiFrame);

        org.eclipse.lsp4j.debug.StackFrame stackFrame = new org.eclipse.lsp4j.debug.StackFrame();
        stackFrame.setId(id);
        Location location = jdiFrame.location();

        // name
        Method method = location.method();
        if (method.isNative()) {
            stackFrame.setName("[Native Method]");
            stackFrame.setPresentationHint(StackFramePresentationHint.SUBTLE);
            Source source = new Source();
            source.setName("[Native Code]");
            source.setPresentationHint(SourcePresentationHint.DEEMPHASIZE);
            stackFrame.setSource(source);
        } else {
            // TODO: pretty print it
            stackFrame.setName(method.name());


            boolean isZsFile = false;
            // source
            Source source = new Source();
            try {
                String sourceName = location.sourceName();
                if (sourceName.endsWith(".zs")) {
                    isZsFile = true;
                    source.setName(sourceName);
                    String sourcePath = location.sourcePath();
                    Path fullPath = context.getScriptRootPath().resolve(sourcePath).normalize();
                    source.setPath(fullPath.toUri().toString());
                } else {
                    if (sourceName.endsWith(".java")) {
                        source.setName(sourceName);
                        stackFrame.setLine(location.lineNumber());
                        try {
                            source.setPath(location.sourcePath());
                        } catch (AbsentInformationException ignored) {
                        }
                    } else {
                        source.setName("[Unknown Source]");
                    }
                    stackFrame.setPresentationHint(StackFramePresentationHint.SUBTLE);
                    source.setPresentationHint(SourcePresentationHint.DEEMPHASIZE);
                }
            } catch (AbsentInformationException ignored) {
            }
            stackFrame.setSource(source);


            if (isZsFile) {
                // range
                try {
                    int line = DAPPositions.fromJDILine(location);
                    stackFrame.setLine(DAPPositions.toDAPLine(line, context));

                    int column = 0;

                    List<Breakpoint> breakpoints = context.getBreakpointManager().findBreakpointsAt(location.sourceName(), line);

                    Optional<Breakpoint> availableBreakpoint = breakpoints.stream().filter(breakpoint -> breakpoint.getRequests()
                            .stream()
                            .filter(it -> it instanceof BreakpointRequest)
                            .map(it -> (BreakpointRequest) it)
                            .map(BreakpointRequest::location)
                            .map(it -> Objects.equals(location, it))
                            .findAny().isPresent()
                    ).findAny();
                    if (availableBreakpoint.isPresent()) {
                        column = availableBreakpoint.get().getPosition().column();
                    }
                    stackFrame.setColumn(DAPPositions.toDAPColumn(column, context));
                } catch (AbsentInformationException ignored) {
                }
            }
        }

        return stackFrame;


    }


}
