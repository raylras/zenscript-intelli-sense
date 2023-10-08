package raylras.zen.dap.jdi;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;
import raylras.zen.dap.debugserver.DebugSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class JDILauncher {

    public static final String HOME = "home";
    public static final String OPTIONS = "options";
    public static final String MAIN = "main";
    public static final String SUSPEND = "suspend";
    public static final String QUOTE = "quote";
    public static final String EXEC = "vmexec";
    public static final String CWD = "cwd";
    public static final String ENV = "env";
    public static final String HOSTNAME = "hostname";
    public static final String PORT = "port";
    public static final String TIMEOUT = "timeout";


    private static final VirtualMachineManager vmManager = Bootstrap.virtualMachineManager();

    /**
     * Attach to an existing debuggee VM.
     *
     * @param hostName      the machine where the debuggee VM is launched on
     * @param port          the debug port that the debuggee VM exposed
     * @param attachTimeout the timeout when attaching to the debuggee VM
     * @return an instance of IDebugSession
     * @throws IOException                        when unable to attach.
     * @throws IllegalConnectorArgumentsException when one of the connector arguments is invalid.
     */
    public static DebugSession attach(String hostName, int port, int attachTimeout)
            throws IOException, IllegalConnectorArgumentsException {
        List<AttachingConnector> connectors = vmManager.attachingConnectors();
        AttachingConnector connector = connectors.get(0);
        // in JDK 10, the first AttachingConnector is not the one we want
        final String SUN_ATTACH_CONNECTOR = "com.sun.tools.jdi.SocketAttachingConnector";
        for (AttachingConnector con : connectors) {
            if (con.getClass().getName().equals(SUN_ATTACH_CONNECTOR)) {
                connector = con;
                break;
            }
        }
        Map<String, Connector.Argument> arguments = connector.defaultArguments();
        arguments.get(HOSTNAME).setValue(hostName);
        arguments.get(PORT).setValue(String.valueOf(port));
        arguments.get(TIMEOUT).setValue(String.valueOf(attachTimeout));
        return new DebugSession(connector.attach(arguments));
    }


    public static ListeningConnector getListeningConnector() {
        List<ListeningConnector> connectors = vmManager.listeningConnectors();
        return connectors.get(0);
    }
    public static Map<String, Connector.Argument> getListenArguments(ListeningConnector connector, int listenTimeout) {
        Map<String, Connector.Argument> arguments = connector.defaultArguments();
        arguments.get(TIMEOUT).setValue(String.valueOf(listenTimeout));
        return arguments;

    }


}
