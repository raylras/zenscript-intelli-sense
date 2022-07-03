package raylras.zen.ls;

import org.eclipse.lsp4j.SemanticTokenTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TokenType {

    CLASS(SemanticTokenTypes.Class),
    PARAMETER(SemanticTokenTypes.Parameter),
    VARIABLE(SemanticTokenTypes.Variable),
    PROPERTY(SemanticTokenTypes.Property),
    EVENT(SemanticTokenTypes.Event),
    FUNCTION(SemanticTokenTypes.Function),
    METHOD(SemanticTokenTypes.Method),
    KEYWORD(SemanticTokenTypes.Keyword),
    MODIFIER(SemanticTokenTypes.Modifier),
    COMMENT(SemanticTokenTypes.Comment),
    STRING(SemanticTokenTypes.String),
    NUMBER(SemanticTokenTypes.Number),
    OPERATOR(SemanticTokenTypes.Operator);
    
    private final String name;

    TokenType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<String> getTokenTypes() {
        return Arrays.stream(TokenType.values()).map(TokenType::getName).collect(Collectors.toList());
    }

}
