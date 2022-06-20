package raylras.zen;

import raylras.zen.launch.SocketLauncher;
import raylras.zen.launch.StandardIOLauncher;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (Arrays.asList(args).contains("-standard-io")) {
            StandardIOLauncher.start();
        } else {
            SocketLauncher.start();
        }
    }

}
