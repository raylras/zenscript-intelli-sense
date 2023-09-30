package raylras.zen.util;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class StopWatch {

    private long start;
    private long stop;

    // Don't change to static.
    // See http://jonamiller.com/2015/12/21/decimalformat-is-not-thread-safe/
    private final DecimalFormat fmt = new DecimalFormat("0.000");

    private StopWatch() {
    }

    public static StopWatch create() {
        return new StopWatch();
    }

    public static StopWatch createAndStart() {
        StopWatch sw = new StopWatch();
        sw.start();
        return sw;
    }

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        stop = System.nanoTime();
    }

    public String getFormattedMillis() {
        return fmt.format(getElapsedMicros() / 1000.0) + "ms";
    }

    private long getElapsedNanos() {
        return stop - start;
    }

    private long getElapsedMicros() {
        return TimeUnit.NANOSECONDS.toMicros(getElapsedNanos());
    }

}
