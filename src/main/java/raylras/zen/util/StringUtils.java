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
}
