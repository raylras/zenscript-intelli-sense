package raylras.zen;

import raylras.zen.launch.ServerLauncher;
import raylras.zen.launch.SocketLauncher;
import raylras.zen.launch.StandardIOLauncher;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ServerLauncher launcher;
        if (Arrays.asList(args).contains("-standard-io")) {
            launcher = new StandardIOLauncher();
        } else {
            launcher = new SocketLauncher();
        }
        launcher.launchServer();
    }

}
