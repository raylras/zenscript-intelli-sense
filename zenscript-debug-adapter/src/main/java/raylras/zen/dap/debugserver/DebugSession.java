package raylras.zen.dap.debugserver;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.ThreadDeathRequest;
import com.sun.jdi.request.ThreadStartRequest;
import raylras.zen.dap.event.EventHub;

public class DebugSession {
    private final VirtualMachine virtualMachine;
    private final EventHub eventHub = new EventHub();

    public DebugSession(VirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    public EventHub eventHub() {
        return eventHub;
    }

    public VirtualMachine getVM() {
        return virtualMachine;
    }


    public void start() {
        ThreadStartRequest threadStartRequest = virtualMachine.eventRequestManager().createThreadStartRequest();
        ThreadDeathRequest threadDeathRequest = virtualMachine.eventRequestManager().createThreadDeathRequest();
        threadStartRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);
        threadDeathRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);
        threadStartRequest.enable();
        threadDeathRequest.enable();
        eventHub.start(virtualMachine);
    }
}
