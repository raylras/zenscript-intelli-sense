package raylras.zen.lsp.util;

import org.apache.commons.text.similarity.LongestCommonSubsequence;

public class TextSimilarity {

    public static final LongestCommonSubsequence algorithmLCS = new LongestCommonSubsequence();

    public static boolean isSubsequence(String left, String right) {
        if (right.isEmpty()) {
            return true;
        } else {
            return lcs(left, right) > 0;
        }
    }

    public static Integer lcs(String left, String right) {
        return algorithmLCS.apply(left, right);
    }

}
