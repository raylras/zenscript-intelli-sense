package raylras.zen.dap.debugserver.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.VMDeathRequest;
import org.eclipse.lsp4j.debug.RunInTerminalRequestArguments;
import org.eclipse.lsp4j.debug.RunInTerminalRequestArgumentsKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.dap.debugserver.DebugAdapterContext;
import raylras.zen.dap.debugserver.DebugSession;
import raylras.zen.dap.jdi.JDILauncher;
import raylras.zen.util.PathUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DebugStartHandler {

    private static final Logger logger = LoggerFactory.getLogger(DebugStartHandler.class);


    public record AttachArgument(
            String hostName,
            int port,
            Path scriptRoot
    ) {
    }

    public record LaunchArgument(
            Path scriptRoot,
            Path javaExecutable,
            boolean launchInTerminal
    ) {
    }

    public static AttachArgument parseAttachArgs(Map<String, Object> args) {

        String hostName = (String) args.get("hostName");
        int port = (int) ((double) args.get("port"));
        String scriptRoot = (String) args.get("scriptRoot");

        return new AttachArgument(hostName, port, Path.of(scriptRoot));
    }


    public static LaunchArgument parseLaunchArgs(Map<String, Object> args) {

        String scriptRoot = (String) args.get("scriptRoot");
        String javaExecutable = (String) args.get("javaExecutable");
        Path scriptRootPath = Path.of(scriptRoot);
        Object terminal = args.get("launchInTerminal");
        boolean inTerminal = !(terminal instanceof Boolean && !((boolean) terminal));
        if (javaExecutable == null) {
            return new LaunchArgument(scriptRootPath, null, inTerminal);
        }
        return new LaunchArgument(scriptRootPath, Path.of(javaExecutable), inTerminal);
    }

    public static boolean handleAttach(AttachArgument attach, DebugAdapterContext context) {
        try {
            logger.info("trying to attach to vm {}:{}", attach.hostName, attach.port);

            DebugSession debugSession = JDILauncher.attach(attach.hostName, attach.port, 100);

            afterStart(attach.scriptRoot, context, debugSession);
            return true;
        } catch (Exception e) {
            logger.error("failed to attach to vm", e);
            return false;
        }
    }

    private static void afterStart(Path scriptRoot, DebugAdapterContext context, DebugSession debugSession) {
        VMDeathRequest vmDeathRequest = debugSession.getVM().eventRequestManager().createVMDeathRequest();
        vmDeathRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);
        vmDeathRequest.setEnabled(true);

        context.setScriptRootPath(scriptRoot);
        context.setDebugSession(debugSession);

        debugSession.start();
        SetBreakpointsHandler.registerBreakpointHandler(context);
    }

    private static final int ATTACH_TERMINAL_TIMEOUT = 20 * 1000;


    public static CompletableFuture<Boolean> handleLaunch(LaunchArgument launchArgument, DebugAdapterContext context) {

        ListeningConnector connector = JDILauncher.getListeningConnector();
        Map<String, Connector.Argument> listenArguments = JDILauncher.getListenArguments(connector, ATTACH_TERMINAL_TIMEOUT);
        String[] commands;
        try {
            String address = connector.startListening(listenArguments);
            commands = constructLaunchCommands(launchArgument, address);
        } catch (IOException | IllegalConnectorArgumentsException e) {
            logger.error("failed to construct launch commands", e);
            return CompletableFuture.completedFuture(false);
        }
        logger.info("trying to attach to launch minecraft with command: {}", String.join(" ", commands));
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        if (launchArgument.launchInTerminal) {
            String[] scriptCommands = generateStartupScript(commands);
            if (scriptCommands == null) {
                return CompletableFuture.completedFuture(false);
            }
            RunInTerminalRequestArguments runArgs = new RunInTerminalRequestArguments();
            runArgs.setArgs(scriptCommands);
            runArgs.setKind(RunInTerminalRequestArgumentsKind.INTEGRATED);
            runArgs.setTitle("Minecraft");
            runArgs.setArgsCanBeInterpretedByShell(false);
            runArgs.setCwd(launchArgument.scriptRoot.resolve("..").normalize().toString());

            context.getClient().runInTerminal(runArgs).whenCompleteAsync((response, e) -> {
                if (e != null) {
                    logger.error("Failed to launch minecraft", e);
                    future.complete(false);
                    try {
                        connector.stopListening(listenArguments);
                    } catch (Exception ignored) {
                    }
                }

                try {
                    VirtualMachine vm = connector.accept(listenArguments);
                    afterStart(launchArgument.scriptRoot, context, new DebugSession(vm));
                    future.complete(true);
                } catch (Exception ex) {
                    logger.error("Failed to launch minecraft", ex);
                    future.complete(false);
                }

            });
        } else {
            CompletableFuture.runAsync(() -> {
                try {
                    Runtime.getRuntime().exec(commands, new String[0], launchArgument.scriptRoot.resolve("..").toFile());
                    try {
                        VirtualMachine vm = connector.accept(listenArguments);
                        afterStart(launchArgument.scriptRoot, context, new DebugSession(vm));
                        future.complete(true);
                    } catch (Exception ex) {
                        logger.error("Failed to launch minecraft", ex);
                        future.complete(false);
                    }
                } catch (IOException e) {
                    logger.error("Failed to launch minecraft", e);
                    future.complete(false);
                }
            });

        }

        return future;
    }


    public static String[] generateStartupScript(String[] args) {
        try {

            List<String> outCommand = new ArrayList<>();
            boolean isWindows = System.getProperty("os.name").startsWith("Windows");

            File scriptFile = File.createTempFile("ZenScriptDAPLaunchMinecraft", isWindows ? ".bat" : ".sh");
            scriptFile.deleteOnExit();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(scriptFile))) {
                if (isWindows) {
                    writer.write("@echo off");

                } else {
                    writer.write("#!/bin/bash");
                }
                writer.newLine();
                for (int i = 0; i < args.length; i++) {
                    if (i != 0) {
                        writer.write(" ");
                    }
                    writer.write(args[i]);
                }
            }

            if (isWindows) {
                // Windows
                outCommand.add("cmd.exe");
                outCommand.add("/c");
            } else {
                outCommand.add("bash");
            }
            outCommand.add(scriptFile.getAbsolutePath());
            return outCommand.toArray(new String[0]);
        } catch (IOException e) {
            logger.error("Failed to generate launch script", e);
            return null;
        }
    }

    private static final Gson GSON = new GsonBuilder()
            .create();

    /**
     * Construct the Java command lines based on the given launch arguments.
     *
     * @param address - the debug port
     * @return the command arrays
     */
    public static String[] constructLaunchCommands(LaunchArgument launchArgument, String address) throws IOException {
        List<String> launchCmds = new ArrayList<>();

        Path path = PathUtil.resolveGeneratedRoot(launchArgument.scriptRoot).resolve("env.json");

        Map<String, Object> args = GSON.fromJson(Files.newBufferedReader(path), new TypeToken<>() {
        });

        String javaPath = (String) args.get("javaPath");
        String classpath = (String) args.get("classpath");
        List<String> jvmFlags = (List<String>) args.get("jvmFlags");
        List<String> launchArgs = (List<String>) args.get("launchArgs");

        if (launchArgument.javaExecutable != null && Files.exists(launchArgument.javaExecutable)) {
            launchCmds.add(launchArgument.javaExecutable.toString());
        } else {
            launchCmds.add(Paths.get(javaPath, "bin", "java").toString());
        }

        launchCmds.add(String.format("-agentlib:jdwp=transport=dt_socket,server=n,suspend=y,address=%s", address));
        for (String jvmFlag : jvmFlags) {
            if (jvmFlag.matches("-agentlib:jdwp=.*")) {
                continue;
            }
            launchCmds.add(jvmFlag);
        }

        launchCmds.add("-cp");
        launchCmds.add(classpath);

        launchCmds.add("net.minecraft.launchwrapper.Launch");

        launchCmds.add("--tweakClass");
        launchCmds.add("net.minecraftforge.fml.common.launcher.FMLTweaker");
        launchCmds.addAll(launchArgs);

        return launchCmds.toArray(new String[0]);
    }

}
