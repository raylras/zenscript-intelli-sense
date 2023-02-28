package raylras.zen.langserver;

import org.eclipse.lsp4j.SemanticTokenModifiers;
import org.eclipse.lsp4j.SemanticTokenTypes;
import org.eclipse.lsp4j.SemanticTokensLegend;
import raylras.zen.code.tree.TreeNode;
import raylras.zen.code.tree.Variable;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Semantics {

    public static final SemanticTokensLegend SEMANTIC_TOKENS_LEGEND;

    static {
        Map<Integer, String> tokenTypes = new TreeMap<>();
        tokenTypes.put(Semantics.TokenType.CLASS, SemanticTokenTypes.Class);
        tokenTypes.put(Semantics.TokenType.PARAMETER, SemanticTokenTypes.Parameter);
        tokenTypes.put(Semantics.TokenType.VARIABLE, SemanticTokenTypes.Variable);
        tokenTypes.put(Semantics.TokenType.FUNCTION, SemanticTokenTypes.Function);
        tokenTypes.put(Semantics.TokenType.METHOD, SemanticTokenTypes.Method);
        tokenTypes.put(Semantics.TokenType.STRING, SemanticTokenTypes.String);

        Map<Integer, String> tokenModifiers = new TreeMap<>();
        tokenModifiers.put(Semantics.TokenModifier.DECLARATION, SemanticTokenModifiers.Declaration);
        tokenModifiers.put(Semantics.TokenModifier.READONLY, SemanticTokenModifiers.Readonly);
        tokenModifiers.put(Semantics.TokenModifier.STATIC, SemanticTokenModifiers.Static);

        SEMANTIC_TOKENS_LEGEND = new SemanticTokensLegend(new ArrayList<>(tokenTypes.values()), new ArrayList<>(tokenModifiers.values()));
    }

    public static int getTokenModifiers(Variable node) {
        int tokenModifiers = 0;
        switch (node.getDeclarator()) {
            case GLOBAL:
            case STATIC:
                tokenModifiers |= TokenModifier.STATIC;
            case VAL:
                tokenModifiers |= TokenModifier.READONLY;
            case VAR:
                tokenModifiers |= TokenModifier.DECLARATION;
        }
        return tokenModifiers;
    }

    public static int getTokenType(TreeNode node) {
        return -1;
    }

    public static final class TokenType {
        public static final int CLASS = 0;
        public static final int PARAMETER = 1;
        public static final int VARIABLE = 2;
        public static final int FUNCTION = 3;
        public static final int METHOD = 4;
        public static final int STRING = 5;
    }

    public static final class TokenModifier {
        public static final int DECLARATION = 1;
        public static final int READONLY = 2;
        public static final int STATIC = 4;
    }

}
