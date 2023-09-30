package raylras.zen.util;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Watcher<T> {

    private long start;
    private long stop;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<T> result;

    private final DecimalFormat fmt = new DecimalFormat("0.000");

    public static <T> Watcher<T> watch(Supplier<Optional<T>> supplier) {
        Watcher<T> watcher = new Watcher<>();
        watcher.start = System.nanoTime();
        watcher.result = supplier.get();
        watcher.stop = System.nanoTime();
        return watcher;
    }

    public static Watcher<Void> watch(Runnable runnable) {
        Watcher<Void> watcher = new Watcher<>();
        watcher.result = Optional.empty();
        watcher.start = System.nanoTime();
        runnable.run();
        watcher.stop = System.nanoTime();
        return watcher;
    }

    public boolean isResultPresent() {
        return result.isPresent();
    }

    public Optional<T> getResult() {
        return result;
    }

    public String getElapsedMillis() {
        return fmt.format(getElapsedMicros() / 1000.0) + "ms";
    }

    private long getElapsedMicros() {
        return TimeUnit.NANOSECONDS.toMicros(stop - start);
    }

}
