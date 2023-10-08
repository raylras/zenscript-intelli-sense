package raylras.zen.dap.debugserver;

import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import org.eclipse.lsp4j.debug.*;
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.dap.debugserver.handler.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
        capabilities.setSupportsTerminateRequest(true);

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
        DebugStartHandler.LaunchArgument attachArgument = DebugStartHandler.parseLaunchArgs(args);
        DebugStartHandler.handleLaunch(attachArgument, context).thenAccept(succeed -> {
            if (succeed) {
                // notify client that we have started jvm and wait for breakpoints
                context.getClient().initialized();
            } else {
                context.getClient().terminated(new TerminatedEventArguments());
            }
        });
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> attach(Map<String, Object> args) {

        DebugStartHandler.AttachArgument attachArgument = DebugStartHandler.parseAttachArgs(args);
        boolean succeed = DebugStartHandler.handleAttach(attachArgument, context);
        if (succeed) {
            // notify client that we have started jvm and wait for breakpoints
            context.getClient().initialized();
        } else {
            context.getClient().terminated(new TerminatedEventArguments());
        }

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> disconnect(DisconnectArguments args) {
        DebugSession debugSession = context.getDebugSession();
        if (debugSession == null) {
            return CompletableFuture.completedFuture(null);
        }
        VirtualMachine vm = debugSession.getVM();
        try {
            if (args.getTerminateDebuggee() != null && args.getTerminateDebuggee()) {
                vm.exit(0);
            } else {
                vm.dispose();
            }
        } catch (VMDisconnectedException ignored) {

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
        ScopesResponse response = new ScopesResponse();
        List<Scope> scopes = ScopeHandler.scopes(args.getFrameId(), context);
        response.setScopes(scopes.toArray(new Scope[0]));
        return CompletableFuture.completedFuture(response);
    }

    @Override
    public CompletableFuture<VariablesResponse> variables(VariablesArguments args) {
        VariablesResponse response = new VariablesResponse();
        List<Variable> variables = VariablesHandler.variables(args, context);
        response.setVariables(variables.toArray(new Variable[0]));
        return CompletableFuture.completedFuture(response);
    }

    @Override
    public CompletableFuture<EvaluateResponse> evaluate(EvaluateArguments args) {
        return IDebugProtocolServer.super.evaluate(args);
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
