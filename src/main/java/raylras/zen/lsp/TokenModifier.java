package raylras.zen.lsp;

import org.eclipse.lsp4j.SemanticTokenModifiers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public enum TokenModifier {

    // if there is no modifier, it means 0 == 0b0.
    // if there is one modifier, such as [declaration], it means 1 == 0b1.
    // if there are two modifiers, such as [declaration, readonly], it means 5 == (0b1 | 0b100) == 0b101.
    // if there are three modifiers, such as [definition, readonly, static], it means 14 == (0b10 | 0b100 | 0b1000) == 0b1110.

    Declaration(0b1, SemanticTokenModifiers.Declaration),
    Definition(0b10, SemanticTokenModifiers.Definition),
    Readonly(0b100, SemanticTokenModifiers.Readonly),
    Static(0b1000, SemanticTokenModifiers.Static);

    private final int id;
    private final String value;

    TokenModifier(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public static int getInt(TokenModifier[] tokenModifiers) {
        int compressed = 0;
        for (TokenModifier tokenModifier : tokenModifiers) {
            compressed = compressed | tokenModifier.id;
        }
        return compressed;
    }

    public static int getInt(Collection<TokenModifier> tokenModifiers) {
        int compressed = 0;
        for (TokenModifier tokenModifier : tokenModifiers) {
            compressed = compressed | tokenModifier.id;
        }
        return compressed;
    }

    public static List<String> getTokenTypeModifiers() {
        return Arrays.stream(TokenModifier.values()).map(TokenModifier::getValue).collect(Collectors.toList());
    }

}
