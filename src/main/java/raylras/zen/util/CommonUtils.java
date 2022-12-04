package raylras.zen.util;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static Path toPath(String uri) {
        return Paths.get(URI.create(uri));
    }



}
