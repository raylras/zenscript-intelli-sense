package raylras.zen.util;

import raylras.zen.code.CompilationUnit;

import java.io.File;
import java.nio.file.Path;

public class ZenClasses {

    public static String getClassName(CompilationUnit unit) {
        return extractClassName(unit.getRelativePath());
    }

    private static String extractClassName(Path path) {
        String classNameWithSlash = path.toString().replace(File.separatorChar, '/');

        // trim extension
        int lastDot = classNameWithSlash.lastIndexOf('.');
        if (lastDot > 0) {
            classNameWithSlash = classNameWithSlash.substring(0, lastDot);
        }

        classNameWithSlash = classNameWithSlash.replace(".", "_");
        classNameWithSlash = classNameWithSlash.replace(" ", "_");

        return classNameWithSlash.replace('/', '.');
    }

}
