package raylras.zen.lsp;

import org.eclipse.lsp4j.SemanticTokenTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ZenTokenType {

    Class(0, SemanticTokenTypes.Class),
    Parameter(1, SemanticTokenTypes.Parameter),
    Variable(2, SemanticTokenTypes.Variable),
    Event(3, SemanticTokenTypes.Event),
    Function(4, SemanticTokenTypes.Function),
    Method(5, SemanticTokenTypes.Method),
    Keyword(6, SemanticTokenTypes.Keyword),
    Modifier(7, SemanticTokenTypes.Modifier),
    Comment(8, SemanticTokenTypes.Comment),
    String(9, SemanticTokenTypes.String),
    Number(10, SemanticTokenTypes.Number),
    Regexp(11, SemanticTokenTypes.Regexp),
    Operator(12, SemanticTokenTypes.Operator);

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
