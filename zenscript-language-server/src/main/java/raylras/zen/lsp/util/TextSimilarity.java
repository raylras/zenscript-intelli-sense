package raylras.zen.lsp.util;

public class TextSimilarity {

    /**
     * Checks if {@code s} is a subsequence of {@code t}.
     * @param s subsequence
     * @param t string
     * @return {@code true} if {@code s} is a subsequence of {@code t}
     */
    public static boolean isSubsequence(String s, String t) {
        int sLen = s.length();
        int tLen = t.length();

        if (sLen == 0) {
            return true;
        }

        int i = 0;
        int j = 0;
        while (i < sLen && j < tLen) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }

            j++;

            if (i == sLen) {
                return true;
            }
        }

        return false;
    }

}
