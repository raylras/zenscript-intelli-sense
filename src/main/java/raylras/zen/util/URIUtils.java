package raylras.zen.util;

import java.net.URI;
import java.nio.file.Paths;

public class URIUtils {

    // Because Unix and Win have different path styles,
    // even though those URIs all refer to the same thing, they are not equal.
    // We need to make sure there is only one style of path.

    private URIUtils() {}

    public static URI create(String uri) {
        try {
            // It's not stupid if it works.
            return Paths.get(URI.create(uri)).toUri();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
