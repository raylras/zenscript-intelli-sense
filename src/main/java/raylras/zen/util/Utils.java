package raylras.zen.util;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Utils {

    private Utils() {}

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

    public static Path findScriptsUpwards(String startDirUri) {
        return findDirUpwards(toPath(startDirUri), "scripts");
    }

    public static Path findDirUpwards(Path startDir, String targetDirName) {
        Path current = startDir;
        while (current != null) {
            Path path = current.resolve(targetDirName);
            if (Files.exists(path) && Files.isDirectory(path)) {
                return path;
            }
            current = current.getParent();
        }
        return null;
    }

}
