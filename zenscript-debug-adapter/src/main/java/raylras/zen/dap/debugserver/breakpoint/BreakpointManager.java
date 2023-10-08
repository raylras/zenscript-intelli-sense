package raylras.zen.dap.debugserver.breakpoint;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.ClassPrepareRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.util.Position;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BreakpointManager {
    private static final Logger logger = LoggerFactory.getLogger(BreakpointManager.class);
    /**
     * A collection of breakpoints registered with this manager.
     */
    private final List<Breakpoint> breakpoints = Collections.synchronizedList(new ArrayList<>(10));
    private final Map<String, HashMap<Position, Breakpoint>> sourceToBreakpoints = new HashMap<>();
    private final AtomicInteger nextBreakpointId = new AtomicInteger(1);

    /**
     * Constructor.
     */
    public BreakpointManager() {
    }

    public List<Breakpoint> findBreakpointsAt(String sourceName, int line) {
        HashMap<Position, Breakpoint> positionBreakpointHashMap = sourceToBreakpoints.get(sourceName);
        if (positionBreakpointHashMap == null) {
            return Collections.emptyList();
        }

        return positionBreakpointHashMap.entrySet().stream()
                .filter(it -> it.getKey().line() == line)
                .map(Map.Entry::getValue)
                .toList();
    }

    public List<Breakpoint> setBreakpoints(String source, List<Breakpoint> breakpoints, boolean sourceModified) {
        List<Breakpoint> result = new ArrayList<>();
        HashMap<Position, Breakpoint> breakpointMap = this.sourceToBreakpoints.get(source);
        // When source file is modified, delete all previously added breakpoints.
        if (sourceModified && breakpointMap != null) {
            for (Breakpoint bp : breakpointMap.values()) {
                try {
                    // Destroy the breakpoint on the debugee VM.
                    bp.close();
                } catch (Exception e) {
                    logger.error("Remove breakpoint exception: ", e);
                }
                this.breakpoints.remove(bp);
            }
            this.sourceToBreakpoints.put(source, null);
            breakpointMap = null;
        }
        if (breakpointMap == null) {
            breakpointMap = new HashMap<>();
            this.sourceToBreakpoints.put(source, breakpointMap);
        }

        // Compute the breakpoints that are newly added.
        List<Breakpoint> toAdd = new ArrayList<>();
        List<Integer> visitedBreakpoints = new ArrayList<>();
        for (Breakpoint breakpoint : breakpoints) {
            Breakpoint existed = breakpointMap.get(breakpoint.getPosition());
            if (existed != null) {
                result.add(existed);
                visitedBreakpoints.add(existed.hashCode());
                continue;
            } else {
                result.add(breakpoint);
            }
            toAdd.add(breakpoint);
        }

        // Compute the breakpoints that are no longer listed.
        List<Breakpoint> toRemove = new ArrayList<>();
        for (Breakpoint breakpoint : breakpointMap.values()) {
            if (!visitedBreakpoints.contains(breakpoint.hashCode())) {
                toRemove.add(breakpoint);
            }
        }

        removeBreakpointsInternally(source, toRemove);
        addBreakpointsInternally(source, toAdd);

        return result;
    }


    private void addBreakpointsInternally(String source, List<Breakpoint> breakpoints) {
        Map<Position, Breakpoint> breakpointMap = this.sourceToBreakpoints.computeIfAbsent(source, k -> new HashMap<>());

        if (breakpoints != null && !breakpoints.isEmpty()) {
            for (Breakpoint breakpoint : breakpoints) {
                breakpoint.setId(this.nextBreakpointId.getAndIncrement());
                this.breakpoints.add(breakpoint);
                breakpointMap.put(breakpoint.getPosition(), breakpoint);
            }
        }
    }

    /**
     * Removes the specified breakpoints from breakpoint manager.
     */
    private void removeBreakpointsInternally(String source, List<Breakpoint> breakpoints) {
        Map<Position, Breakpoint> breakpointMap = this.sourceToBreakpoints.get(source);
        if (breakpointMap == null || breakpointMap.isEmpty() || breakpoints.isEmpty()) {
            return;
        }

        for (Breakpoint breakpoint : breakpoints) {
            if (this.breakpoints.contains(breakpoint)) {
                try {
                    // Destroy the breakpoint on the debugee VM.
                    breakpoint.close();
                    this.breakpoints.remove(breakpoint);
                    breakpointMap.remove(breakpoint.getPosition());
                } catch (Exception e) {
                    logger.error("Remove breakpoint exception", e);
                }
            }
        }
    }
}
