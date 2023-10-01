package raylras.zen.util;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PathUtil {

    private PathUtil() {}

    public static Path toPath(String uri) {
        try {
            return Paths.get(URI.create(uri)).toRealPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if {@code s} is a sub path of {@code p}.
     * @param s sub path
     * @param p parent path
     * @return {@code true} if {@code s} is a sub path of {@code t}
     */
    public static boolean isSubPath(Path s, Path p) {
        return s.toString().startsWith(p.toString());
    }

    public static String getFileName(String uri) {
        return getFileName(toPath(uri));
    }

    public static String getFileName(Path path) {
        return path.toFile().getName();
    }

    public static String getFileNameWithoutSuffix(Path path) {
        String fileName = getFileName(path);
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    public static Path findUpwardsOrSelf(Path start, String targetName) {
        Path target = findUpwards(start, targetName);
        return (target != null) ? target : start;
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

    public static String toHash(Path path) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] bytes = path.toString().getBytes(StandardCharsets.UTF_8);
            BigInteger hash = new BigInteger(1, sha1.digest(bytes));
            return hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            // Should never happen
            return "0";
        }
    }

}
