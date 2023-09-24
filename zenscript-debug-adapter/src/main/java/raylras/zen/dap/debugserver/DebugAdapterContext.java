package raylras.zen.dap.debugserver;

import raylras.zen.dap.debugserver.breakpoint.BreakpointManager;
import raylras.zen.dap.debugserver.runtime.StackFrameManager;
import raylras.zen.dap.debugserver.runtime.StepState;
import raylras.zen.dap.debugserver.runtime.ThreadManager;

import java.nio.file.Path;

public class DebugAdapterContext{

    private boolean lineStartAt1;
    private boolean columnStartAt1;
    private boolean initialized;
    private DebugSession debugSession;
    private IZenDebugProtocolClient client;

    private final BreakpointManager breakpointManager = new BreakpointManager();
    private final StackFrameManager stackFrameManager = new StackFrameManager();
    private final ThreadManager threadManager = new ThreadManager();
    private StepState pendingStep = null;

    private Path scriptRootPath;

    public Path getScriptRootPath() {
        return scriptRootPath;
    }

    public void setScriptRootPath(Path scriptRootPath) {
        this.scriptRootPath = scriptRootPath;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public DebugSession getDebugSession() {
        return debugSession;
    }

    public void setDebugSession(DebugSession debugSession) {
        this.debugSession = debugSession;
    }

    public BreakpointManager getBreakpointManager() {
        return breakpointManager;
    }

    public IZenDebugProtocolClient getClient() {
        return client;
    }

    public void setClient(IZenDebugProtocolClient client) {
        this.client = client;
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public boolean isLineStartAt1() {
        return lineStartAt1;
    }

    public void setLineStartAt1(boolean lineStartAt1) {
        this.lineStartAt1 = lineStartAt1;
    }

    public boolean isColumnStartAt1() {
        return columnStartAt1;
    }

    public void setColumnStartAt1(boolean columnStartAt1) {
        this.columnStartAt1 = columnStartAt1;
    }

    public StackFrameManager getStackFrameManager() {
        return stackFrameManager;
    }

    public StepState getPendingStep() {
        return pendingStep;
    }

    public void setPendingStep(StepState pendingStep) {
        this.pendingStep = pendingStep;
    }
}
