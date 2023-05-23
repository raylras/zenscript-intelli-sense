package raylras.zen.util;

public final class StringUtils {
    /**
     * matches partial name
     * eg: abef -> abcdef
     */
    public static boolean matchesPartialName(String candidate, String partialName) {
        if (candidate.length() < partialName.length()) return false;

        int i = 0, j = 0;

        while (i < partialName.length() && j < candidate.length()) {
            char existingChar = partialName.charAt(i);
            char candidateChar = candidate.charAt(i);
            if (candidateChar == existingChar) {
                i++;
            }
            j++;
        }
        return i == partialName.length();
    }


    public static String getPackageName(String fullQualifiedName) {

        int lastDotIndex = fullQualifiedName.lastIndexOf(".");
        if (lastDotIndex <= 0) {
            return "";
        }

        return fullQualifiedName.substring(0, lastDotIndex);
    }

    public static String getSimpleName(String fullQualifiedName) {
        int lastDotIndex = fullQualifiedName.lastIndexOf(".");
        if (lastDotIndex < 0) {
            return fullQualifiedName;
        }

        return fullQualifiedName.substring(lastDotIndex + 1);
    }

    public static String getBeforeFirstDot(String fullQualifiedName) {
        int dotIndex = fullQualifiedName.indexOf(".");
        if (dotIndex < 0) {
            return fullQualifiedName;
        }

        return fullQualifiedName.substring(0, dotIndex);
    }
}
