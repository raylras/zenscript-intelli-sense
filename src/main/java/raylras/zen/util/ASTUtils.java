package raylras.zen.util;

import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.lsp.TokenModifier;

public class ASTUtils {

    private ASTUtils() {}

    public static int getModifiers(VariableDeclStatement variable) {
        int modifier = 0;
        if (variable.isStatic()) modifier |= TokenModifier.Static.getId();
        if (variable.isFinal()) modifier |= TokenModifier.Readonly.getId();
        return modifier;
    }

}
