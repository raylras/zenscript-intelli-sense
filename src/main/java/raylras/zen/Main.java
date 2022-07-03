package raylras.zen;

import org.apache.commons.cli.*;
import raylras.zen.launch.SocketLauncher;
import raylras.zen.launch.StandardIOLauncher;

public class Main {

    private static final Options OPTIONS = new Options();

    static {
        OPTIONS.addOption(Option.builder()
                .option("s")
                .longOpt("socket")
                .argName("port")
                .hasArg(true)
                .optionalArg(true)
                .desc("launch server via socket, default port is " + SocketLauncher.DEFAULT_SOCKET_PORT)
                .build()
        );
        OPTIONS.addOption(Option.builder()
                .option("i")
                .longOpt("stdio")
                .desc("launch server via stdio")
                .build()
        );
    }

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(OPTIONS, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp("java -jar lsp4zen.jar", OPTIONS);
            return;
        }

        if (cmd.hasOption("socket")) {
            int port = Integer.parseInt(cmd.getOptionValue("socket", String.valueOf(SocketLauncher.DEFAULT_SOCKET_PORT)));
            new SocketLauncher().launchServer(port);
        } else if (cmd.hasOption("stdio")) {
            new StandardIOLauncher().launchServer();
        } else {
            System.out.println("No option specified");
            new HelpFormatter().printHelp("java -jar lsp4zen.jar", OPTIONS);
        }
    }

}
