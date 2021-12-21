package raylras.zen.lsp;

import org.eclipse.lsp4j.SemanticTokenModifiers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public enum ZenTokenTypeModifier {

    // 0b0 == 0, 0 means [], no modifier.

    // 0b1 == 1, 1 means [Definition], it has one modifier. in java be like: int foo;
    // 0b10 == 2, 2 means [Readonly], it has one modifier.
    // 0b100 == 4, 4 means [Static], it has one modifier.

    // (0b1 | 0b10) == 0b11 == 3, 3 means [Definition, Readonly], it has two modifiers. in java be like: final int foo;
    // (0b1 | 0b10 | 0b100) == 0b111 == 7, 7 means [Definition, Readonly, Static], it has three modifiers. in java be like: static final int foo;

    Definition(0b1/* 0b1 == 1 << 0 == 1 */, SemanticTokenModifiers.Definition),
    Readonly(0b10/* 0b10 == 1 << 1 == 2 */, SemanticTokenModifiers.Readonly),
    Static(0b100/* 0b100 == 1 << 2 == 4 */, SemanticTokenModifiers.Static);

    private final int id;
    private final String value;

    ZenTokenTypeModifier(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public static int getInt(ZenTokenTypeModifier[] tokenModifiers) {
        int compressed = 0;
        for (ZenTokenTypeModifier tokenModifier : tokenModifiers) {
            compressed = compressed | tokenModifier.id;
        }
        return compressed;
    }

    public static int getInt(Collection<ZenTokenTypeModifier> tokenModifiers) {
        int compressed = 0;
        for (ZenTokenTypeModifier tokenModifier : tokenModifiers) {
            compressed = compressed | tokenModifier.id;
        }
        return compressed;
    }

    public static List<String> getTokenTypeModifiers() {
        return Arrays.stream(ZenTokenTypeModifier.values()).map(ZenTokenTypeModifier::getValue).collect(Collectors.toList());
    }

}
