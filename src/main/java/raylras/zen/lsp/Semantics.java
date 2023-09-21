package raylras.zen.lsp;

import org.eclipse.lsp4j.SemanticTokenModifiers;
import org.eclipse.lsp4j.SemanticTokenTypes;
import org.eclipse.lsp4j.SemanticTokensLegend;
import raylras.zen.model.symbol.Symbol.Modifier;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Semantics {

    public static final SemanticTokensLegend SEMANTIC_TOKENS_LEGEND;

    static {
        List<String> tokenTypes = Arrays.stream(TokenType.values()).map(tokenType -> tokenType.name).collect(Collectors.toList());
        List<String> tokenModifiers = Arrays.stream(TokenModifier.values()).map(tokenModifier -> tokenModifier.name).collect(Collectors.toList());
        SEMANTIC_TOKENS_LEGEND = new SemanticTokensLegend(tokenTypes, tokenModifiers);
    }

    public static int getTokenModifiers(Modifier modifier) {
        int tokenModifiers = 0;
        switch (modifier) {
            case STATIC:
            case GLOBAL:
                tokenModifiers |= TokenModifier.STATIC.flag;
            case VAL:
                tokenModifiers |= TokenModifier.READONLY.flag;
            case VAR:
            case NONE:
                tokenModifiers |= TokenModifier.DEFINITION.flag;
        }
        return tokenModifiers;
    }

    public enum TokenType {
        TYPE(SemanticTokenTypes.Type),
        CLASS(SemanticTokenTypes.Class),
        PARAMETER(SemanticTokenTypes.Parameter),
        VARIABLE(SemanticTokenTypes.Variable),
        FUNCTION(SemanticTokenTypes.Function),
        KEYWORD(SemanticTokenTypes.Keyword),
        MODIFIER(SemanticTokenTypes.Modifier),
        COMMENT(SemanticTokenTypes.Comment),
        STRING(SemanticTokenTypes.String),
        NUMBER(SemanticTokenTypes.Number),
        OPERATOR(SemanticTokenTypes.Operator);

        public final String name;

        TokenType(String name) {
            this.name = name;
        }
    }

    public enum TokenModifier {
        DEFINITION(1, SemanticTokenModifiers.Definition),
        READONLY(2, SemanticTokenModifiers.Readonly),
        STATIC(4, SemanticTokenModifiers.Static);

        public final int flag;
        public final String name;

        TokenModifier(int flag, String name) {
            this.flag = flag;
            this.name = name;
        }
    }

}
