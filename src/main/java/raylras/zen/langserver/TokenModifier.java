package raylras.zen.langserver;

import org.eclipse.lsp4j.SemanticTokenModifiers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TokenModifier {

    Declaration(0b1, SemanticTokenModifiers.Declaration),
    Definition(0b10, SemanticTokenModifiers.Definition),
    Readonly(0b100, SemanticTokenModifiers.Readonly),
    Static(0b1000, SemanticTokenModifiers.Static);

    private final int id;
    private final String name;

    TokenModifier(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static int toInt(TokenModifier... modifiers) {
        int flag = 0;
        for (TokenModifier modifier : modifiers) {
            flag |= modifier.id;
        }
        return flag;
    }

    public static List<String> getTokenModifiers() {
        return Stream.of(TokenModifier.values()).map(TokenModifier::getName).collect(Collectors.toList());
    }

}
