package raylras.zen.dap.debugserver;

import com.sun.jdi.*;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.VMDeathRequest;
import org.eclipse.lsp4j.debug.*;
import org.eclipse.lsp4j.debug.Thread;
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.dap.DAPException;
import raylras.zen.dap.debugserver.handler.*;
import raylras.zen.dap.jdi.JDILauncher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class ZenDebugAdapter implements IDebugProtocolServer {
    private final DebugAdapterContext context = new DebugAdapterContext();
    private static final Logger logger = LoggerFactory.getLogger(ZenDebugAdapter.class);

    @Override
    public CompletableFuture<Capabilities> initialize(InitializeRequestArguments args) {
        Capabilities capabilities = new Capabilities();
        capabilities.setSupportsConfigurationDoneRequest(true);
        capabilities.setSupportsSingleThreadExecutionRequests(true);
        capabilities.setSupportsSteppingGranularity(true);
        capabilities.setSupportsDelayedStackTraceLoading(true);

        capabilities.setSupportsStepBack(false);
        capabilities.setSupportsInstructionBreakpoints(false);
        capabilities.setSupportsTerminateThreadsRequest(false);
        capabilities.setSupportSuspendDebuggee(false);

        context.setLineStartAt1(args.getLinesStartAt1());
        context.setColumnStartAt1(args.getColumnsStartAt1());
        context.setInitialized(true);

        return CompletableFuture.completedFuture(capabilities);
    }

    @Override
    public CompletableFuture<Void> configurationDone(ConfigurationDoneArguments args) {
        ConfigurationDoneHandler.handle(context);
        context.getDebugSession().getVM().resume();
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> launch(Map<String, Object> args) {
        return IDebugProtocolServer.super.launch(args);
    }

    @Override
    public CompletableFuture<Void> attach(Map<String, Object> args) {

        try {
            String hostName = (String) args.get("hostName");
            int port = (int) ((double) args.get("port"));
            String projectRoot = (String) args.get("projectRoot");
            logger.info("trying to attach to vm {}:{}", hostName, port);
            DebugSession debugSession = JDILauncher.attach(hostName, port, 100);

            VMDeathRequest vmDeathRequest = debugSession.getVM().eventRequestManager().createVMDeathRequest();
            vmDeathRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);
            vmDeathRequest.setEnabled(true);

            context.setScriptRootPath(Path.of(projectRoot));
            context.setDebugSession(debugSession);

            debugSession.start();
            SetBreakpointsHandler.registerBreakpointHandler(context);
        } catch (Exception e) {
            logger.error("failed to attach to vm", e);
            throw new DAPException(e);
        }
        // notify client that we have started jvm and wait for breakpoints
        context.getClient().initialized();
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> disconnect(DisconnectArguments args) {
        DebugSession debugSession = context.getDebugSession();
        if (debugSession == null) {
            return CompletableFuture.completedFuture(null);
        }
        VirtualMachine vm = debugSession.getVM();
        if (args.getTerminateDebuggee() != null && args.getTerminateDebuggee()) {
            vm.exit(0);
        } else {
            vm.dispose();
        }

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> terminate(TerminateArguments args) {
        DebugSession debugSession = context.getDebugSession();
        if (debugSession != null) {
            debugSession.getVM().exit(0);
        }
        return CompletableFuture.completedFuture(null);
    }

    public void connect(IZenDebugProtocolClient remoteProxy) {
        context.setClient(remoteProxy);
    }


    @Override
    public CompletableFuture<BreakpointLocationsResponse> breakpointLocations(BreakpointLocationsArguments args) {
        return IDebugProtocolServer.super.breakpointLocations(args);
    }

    @Override
    public CompletableFuture<SetBreakpointsResponse> setBreakpoints(SetBreakpointsArguments args) {
        SetBreakpointsResponse response = new SetBreakpointsResponse();
        Breakpoint[] breakpoints = SetBreakpointsHandler.setBreakpoints(context, args);
        response.setBreakpoints(breakpoints);
        return CompletableFuture.completedFuture(response);
    }


    @Override
    public CompletableFuture<SetFunctionBreakpointsResponse> setFunctionBreakpoints(SetFunctionBreakpointsArguments args) {
        return IDebugProtocolServer.super.setFunctionBreakpoints(args);
    }

    @Override
    public CompletableFuture<SetExceptionBreakpointsResponse> setExceptionBreakpoints(SetExceptionBreakpointsArguments args) {
        // TODO
        return CompletableFuture.completedFuture(new SetExceptionBreakpointsResponse());
    }

    @Override
    public CompletableFuture<DataBreakpointInfoResponse> dataBreakpointInfo(DataBreakpointInfoArguments args) {
        return IDebugProtocolServer.super.dataBreakpointInfo(args);
    }

    @Override
    public CompletableFuture<SetDataBreakpointsResponse> setDataBreakpoints(SetDataBreakpointsArguments args) {
        return IDebugProtocolServer.super.setDataBreakpoints(args);
    }

    @Override
    public CompletableFuture<ThreadsResponse> threads() {
        ThreadsResponse response = ThreadListHandler.visibleThreads(context);
        return CompletableFuture.completedFuture(response);
    }


    @Override
    public CompletableFuture<StackTraceResponse> stackTrace(StackTraceArguments args) {
        StackTraceResponse stackTrace;
        try {
            stackTrace = StackTraceHandler.getStackTrace(context, args);
        } catch (IncompatibleThreadStateException e) {
            logger.error("Could not get stack trace of thread {}", args.getThreadId(), e);
            return CompletableFuture.completedFuture(new StackTraceResponse());
        }

        return CompletableFuture.completedFuture(stackTrace);
    }

    @Override
    public CompletableFuture<ScopesResponse> scopes(ScopesArguments args) {
        // TODO: scopes
        ScopesResponse response = new ScopesResponse();
        response.setScopes(new Scope[0]);
        return CompletableFuture.completedFuture(response);
    }

    @Override
    public CompletableFuture<VariablesResponse> variables(VariablesArguments args) {
        // TODO variables
        VariablesResponse response = new VariablesResponse();
        response.setVariables(new Variable[0]);
        return CompletableFuture.completedFuture(response);
    }


    @Override
    public CompletableFuture<ContinueResponse> continue_(ContinueArguments args) {
        ContinueResponse response = new ContinueResponse();
        boolean allContinued = DebugJumpHandler.continue_(args, context);
        response.setAllThreadsContinued(allContinued);
        return CompletableFuture.completedFuture(response);
    }

    @Override
    public CompletableFuture<SourceResponse> source(SourceArguments args) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> next(NextArguments args) {
        DebugJumpHandler.next(args, context);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> stepIn(StepInArguments args) {
        DebugJumpHandler.stepIn(args, context);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> stepOut(StepOutArguments args) {
        DebugJumpHandler.stepOut(args, context);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> goto_(GotoArguments args) {
//        DebugJumpHandler.goto_(args, context);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> pause(PauseArguments args) {
        DebugJumpHandler.pause(args.getThreadId(), context);
        return CompletableFuture.completedFuture(null);
    }


    @Override
    public CompletableFuture<StepInTargetsResponse> stepInTargets(StepInTargetsArguments args) {
        return IDebugProtocolServer.super.stepInTargets(args);
    }

    @Override
    public CompletableFuture<GotoTargetsResponse> gotoTargets(GotoTargetsArguments args) {
        return IDebugProtocolServer.super.gotoTargets(args);
    }


}
