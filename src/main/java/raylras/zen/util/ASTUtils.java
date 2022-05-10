package raylras.zen.util;

import raylras.zen.ast.VariableNode;
import raylras.zen.lsp.TokenModifier;

public class ASTUtils {

    public static int getModifiers(VariableNode variable) {
        int modifier = 0;
        if (variable.isStatic()) modifier |= TokenModifier.Static.getId();
        if (variable.isFinal()) modifier |= TokenModifier.Readonly.getId();
        return modifier;
    }

}
