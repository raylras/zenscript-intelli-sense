package raylras.zen.util;

import raylras.zen.code.CompilationUnit;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathUtils {

    private PathUtils() {}

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

    public static boolean isSourceFile(Path path) {
        return isZsFile(path) || isDzsFile(path);
    }

    public static boolean isZsFile(Path path) {
        return String.valueOf(path).endsWith(CompilationUnit.ZS_FILE_EXTENSION);
    }

    public static boolean isDzsFile(Path path) {
        for (String ext : CompilationUnit.DZS_FILE_EXTENSIONS) {
            if (path.toString().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

}
