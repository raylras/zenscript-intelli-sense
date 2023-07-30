package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.Declarator;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptLexer;
import raylras.zen.code.parser.ZenScriptParser.*;

import java.util.Objects;

public final class DeclaratorResolver {

    private DeclaratorResolver() {}

    public static Declarator getDeclarator(ParseTree cst) {
        Objects.requireNonNull(cst);
        return cst.accept(DeclaratorVisitor.INSTANCE);
    }

    private static final class DeclaratorVisitor extends Visitor<Declarator> {
        private static final DeclaratorVisitor INSTANCE = new DeclaratorVisitor();

        @Override
        public Declarator visitParameter(ParameterContext ctx) {
            return Declarator.NONE;
        }

        @Override
        public Declarator visitVariableDeclaration(VariableDeclarationContext ctx) {
            switch (ctx.Declarator.getType()) {
                case ZenScriptLexer.VAR:
                    return Declarator.VAR;

                case ZenScriptLexer.VAL:
                    return Declarator.VAL;

                case ZenScriptLexer.GLOBAL:
                    return Declarator.GLOBAL;

                case ZenScriptLexer.STATIC:
                    return Declarator.STATIC;

                case ZenScriptLexer.EXPAND:
                    return Declarator.EXPAND;

                default:
                    return null;
            }
        }

        @Override
        public Declarator visitForeachVariableDeclaration(ForeachVariableDeclarationContext ctx) {
            return Declarator.NONE;
        }

        @Override
        public Declarator visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            if (ctx.Declarator != null
                    && ctx.Declarator.getType() == ZenScriptLexer.STATIC) {
                return Declarator.STATIC;
            } else {
                return null;
            }
        }

        @Override
        public Declarator visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            return Declarator.EXPAND;
        }
    }

}
