package raylras.zen.lsp;

import org.eclipse.lsp4j.SemanticTokenTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TokenType {

    Class(0, SemanticTokenTypes.Class),
    Parameter(1, SemanticTokenTypes.Parameter),
    Variable(2, SemanticTokenTypes.Variable),
    Property(3, SemanticTokenTypes.Property),
    Event(4, SemanticTokenTypes.Event),
    Function(5, SemanticTokenTypes.Function),
    Method(6, SemanticTokenTypes.Method),
    Keyword(7, SemanticTokenTypes.Keyword),
    Modifier(8, SemanticTokenTypes.Modifier),
    Comment(9, SemanticTokenTypes.Comment),
    String(10, SemanticTokenTypes.String),
    Number(11, SemanticTokenTypes.Number),
    Regexp(12, SemanticTokenTypes.Regexp),
    Operator(13, SemanticTokenTypes.Operator);

    private final int id;
    private final String value;

    TokenType(int id, String value) {
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
        return Arrays.stream(TokenType.values()).map(TokenType::getValue).collect(Collectors.toList());
    }

}
