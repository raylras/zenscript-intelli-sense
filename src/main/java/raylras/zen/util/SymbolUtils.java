package raylras.zen.util;

import com.google.common.collect.ImmutableSet;
import org.antlr.v4.runtime.*;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.type.Type;
import raylras.zen.code.type.resolve.LiteralTypeResolver;
import raylras.zen.code.type.resolve.NameResolver;
import raylras.zen.service.LibraryService;

import java.util.*;

public class SymbolUtils {


    public static Type parseTypeLiteral(String text, CompilationUnit unit) {

        ZenScriptParser parser = new ZenScriptParser(new CommonTokenStream(new ZenScriptLexer(CharStreams.fromString(text))));
        parser.removeErrorListeners();
        ZenScriptParser.TypeLiteralContext literal = parser.typeLiteral();
        return new LiteralTypeResolver(unit).resolve(literal);
    }


    public static Map<String, String> getAnnotations(CompilationUnit unit, Token locator, Set<String> validToken) {

        List<Token> tokens = unit.tokenStream.getHiddenTokensToLeft(
            locator.getTokenIndex(),
            ZenScriptParser.LINE_COMMENT
        );

        if (tokens == null) {
            return Collections.emptyMap();
        }

        Map<String, String> result = new HashMap<>();
        for (Token token : tokens) {
            String annotation = token.getText().substring(2).trim();

            if (annotation.charAt(0) != '$') {
                continue;
            }

            String[] split = annotation.split(":", 2);

            String head = split[0].substring(1);
            if (validToken.contains(head)) {
                if (split.length < 2) {
                    result.put(head, "");
                } else {
                    result.put(head, split[1].trim());
                }
            }

        }
        return result;
    }

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
