package raylras.zen.util;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public final class Utils {

    private Utils() {
    }

    public static Path toPath(String uri) {
        return Paths.get(URI.create(uri));
    }

    public static boolean isSubPath(String parentUri, String childUri) {
        return isSubPath(toPath(parentUri), toPath(childUri));
    }

    public static boolean isSubPath(Path parent, Path child) {
        return child.toString().startsWith(parent.toString());
    }

    public static String getFileName(String uri) {
        return getFileName(toPath(uri));
    }

    public static String getFileName(Path path) {
        return path.toFile().getName();
    }

    public static Path findUpwards(Path start, String targetName) {
        Path current = start;
        while (current != null) {
            Path target = current.resolve(targetName);
            if (Files.exists(target)) {
                return target;
            }
            current = current.getParent();
        }
        return null;
    }

    public static void logLongTime(Instant started, long maxDurationMS, Consumer<Long> callback) {
        long duration = Duration.between(started, Instant.now()).toMillis();
        if (duration >= maxDurationMS) {
            callback.accept(duration);
        }
    }
}
