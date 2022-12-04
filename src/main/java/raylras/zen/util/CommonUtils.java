package raylras.zen.util;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static Path toPath(String uri) {
        return Paths.get(URI.create(uri));
    }

    public static String getFileName(Path path) {
        return path.toFile().getName();
    }

    public static String getFileName(String uri) {
        return getFileName(toPath(uri));
    }

    public static String getPackageName(Path projectRoot, Path documentPath) {
        String str = projectRoot.relativize(documentPath).toString();
        return str.replace(File.separator, "/").substring(0, str.lastIndexOf("."));
    }



}
