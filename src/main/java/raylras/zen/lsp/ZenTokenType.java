package raylras.zen.lsp;

import org.eclipse.lsp4j.SemanticTokenTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ZenTokenType {

    CLASS(0, SemanticTokenTypes.Class),
    PARAMETER(1, SemanticTokenTypes.Parameter),
    VARIABLE(2, SemanticTokenTypes.Variable),
    EVENT(3, SemanticTokenTypes.Event),
    FUNCTION(4, SemanticTokenTypes.Function),
    METHOD(5, SemanticTokenTypes.Method),
    KEYWORD(6, SemanticTokenTypes.Keyword),
    MODIFIER(7, SemanticTokenTypes.Modifier),
    COMMENT(8, SemanticTokenTypes.Comment),
    STRING(9, SemanticTokenTypes.String),
    NUMBER(10, SemanticTokenTypes.Number),
    REGEXP(11, SemanticTokenTypes.Regexp),
    OPERATOR(12, SemanticTokenTypes.Operator);

    private final int id;
    private final String value;

    ZenTokenType(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public static List<String> getTokenTypes() {
        return Arrays.stream(ZenTokenType.values()).map(ZenTokenType::getValue).collect(Collectors.toList());
    }

}
