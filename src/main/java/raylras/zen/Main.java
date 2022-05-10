package raylras.zen;

import raylras.zen.launch.SocketLauncher;
import raylras.zen.launch.StandardIOLauncher;

public class Main {
    public static void main(String[] args) {
        for (String arg : args) {
            if ("-standard-io".equals(arg)) {
                StandardIOLauncher.start();
            }
        }
        SocketLauncher.start();
    }

}
