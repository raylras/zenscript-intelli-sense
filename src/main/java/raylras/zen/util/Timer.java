package raylras.zen.util;

public class Timer {

    private final long startTime;

    public Timer() {
        this.startTime = System.currentTimeMillis();
    }

    public String cost() {
        return (System.currentTimeMillis() - startTime) + "ms";
    }

}
