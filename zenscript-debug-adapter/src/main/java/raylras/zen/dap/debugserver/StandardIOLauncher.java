package raylras.zen.dap.debugserver;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.debug.DebugLauncher;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.slf4j.LoggerFactory;
import raylras.zen.dap.DAPException;
import raylras.zen.dap.DAPLogAppender;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StandardIOLauncher {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ZenDebugAdapter.class);


    public static void main(String[] args) {
        start();
    }

    public static void start() {
        ZenDebugAdapter debugAdapter = new ZenDebugAdapter();
        ExecutorService threads = Executors.newSingleThreadExecutor();

        Launcher<IZenDebugProtocolClient> serverLauncher = new DebugLauncher.Builder<IZenDebugProtocolClient>()
                .setLocalService(debugAdapter)
                .setRemoteInterface(IZenDebugProtocolClient.class)
                .setInput(System.in)
                .setOutput(System.out)
                .setExecutorService(threads)
                .setExceptionHandler(StandardIOLauncher::handleException)
                .create();

        IZenDebugProtocolClient client = serverLauncher.getRemoteProxy();
        initializeLogger(client);
        debugAdapter.connect(client);
        serverLauncher.startListening();
    }

    private static void initializeLogger(IZenDebugProtocolClient client) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        Logger rootLogger = loggerContext.getLogger("ROOT");
        PatternLayout layout = new PatternLayout();
        layout.setContext(loggerContext);
        layout.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        layout.start();
        DAPLogAppender dapLogAppender = new DAPLogAppender(client);
        dapLogAppender.setLayout(layout);
        dapLogAppender.setContext(loggerContext);
        dapLogAppender.start();
        rootLogger.addAppender(dapLogAppender);
    }

    private static ResponseError handleException(Throwable throwable) {
        if (throwable instanceof CompletionException || throwable instanceof InvocationTargetException && throwable.getCause() != null) {
            return handleException(throwable.getCause());
        }

        if (throwable instanceof DAPException) {

            return new ResponseError(
                    1,
                    throwable.getMessage(),
                    throwable
            );
        }

        if (throwable instanceof ResponseErrorException responseError) {
            return responseError.getResponseError();
        }


        logger.error("Internal Error", throwable);
        ResponseError error = new ResponseError();
        error.setMessage("Internal Error");
        error.setCode(ResponseErrorCode.InternalError);
        ByteArrayOutputStream stackTrace = new ByteArrayOutputStream();
        PrintWriter stackTraceWriter = new PrintWriter(stackTrace);
        throwable.printStackTrace(stackTraceWriter);
        stackTraceWriter.flush();
        error.setData(stackTrace.toString());
        return error;
    }

}

