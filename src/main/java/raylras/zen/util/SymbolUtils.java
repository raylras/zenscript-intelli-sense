package raylras.zen.util;

import com.google.common.collect.ImmutableSet;
import org.antlr.v4.runtime.ParserRuleContext;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.resolve.NameResolver;

import java.util.Set;

public class SymbolUtils {

    private static Set<String> nativeClasses = ImmutableSet.of(
        "string",
        "int",
        "byte",
        "short",
        "long",
        "float",
        "double",
        "boolean"
    );

    public static boolean isNativeClass(String className) {
        return nativeClasses.contains(className);
    }

    public static String inferExpressionName(ZenScriptParser.ExpressionContext expr) {
        ParserRuleContext parent = expr.getParent();

        if (parent instanceof ZenScriptParser.TypeCastExprContext) {
            return inferExpressionName((ZenScriptParser.ExpressionContext) parent);
        }

        if (parent instanceof ZenScriptParser.AssignmentExprContext) {
            ZenScriptParser.AssignmentExprContext assignmentExpr = (ZenScriptParser.AssignmentExprContext) parent;
            if (assignmentExpr.Op.getType() != ZenScriptParser.EQUAL) {
                return null;
            }
            return new NameResolver().resolve(assignmentExpr.Left);
        }


        if (parent instanceof ZenScriptParser.MapEntryContext) {
            return new NameResolver().resolve(((ZenScriptParser.MapEntryContext) parent).Key);
        }

        if (parent instanceof ZenScriptParser.InitializerContext) {
            ZenScriptParser.VariableDeclarationContext variableDeclaration = (ZenScriptParser.VariableDeclarationContext) parent.parent;
            return new NameResolver().resolve(variableDeclaration.simpleName());
        }

        if (parent instanceof ZenScriptParser.DefaultValueContext) {
            ZenScriptParser.ParameterContext variableDeclaration = (ZenScriptParser.ParameterContext) parent.parent;
            return new NameResolver().resolve(variableDeclaration.simpleName());
        }

        return null;

    }

}
