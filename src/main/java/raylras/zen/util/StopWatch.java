package raylras.zen.util;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class StopWatch {

    private long start;
    private long stop;

    // Don't change to static.
    // See http://jonamiller.com/2015/12/21/decimalformat-is-not-thread-safe/
    private final DecimalFormat fmt = new DecimalFormat("0.000");

    public StopWatch() {}

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        stop = System.nanoTime();
    }

    public void reset() {
        start = 0;
        stop = 0;
    }

    public void restart() {
        reset();
        start();
    }

    public long getElapsedNanos() {
        return stop - start;
    }

    public long getElapsedMicros() {
        return TimeUnit.NANOSECONDS.toMicros(getElapsedNanos());
    }

    public String getFormattedMillis() {
        return fmt.format(getElapsedMicros() / 1000.0);
    }

}
