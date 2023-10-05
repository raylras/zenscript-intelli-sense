package raylras.zen.model.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.model.Visitor;
import raylras.zen.model.parser.ZenScriptLexer;
import raylras.zen.model.parser.ZenScriptParser.*;
import raylras.zen.model.symbol.Symbol.Modifier;
import raylras.zen.util.CSTNodes;

import java.util.Objects;
import java.util.Optional;

public final class ModifierResolver {

    private ModifierResolver() {}

    public static Optional<Modifier> getModifier(ParseTree cst) {
        Objects.requireNonNull(cst);
        return Optional.ofNullable(cst.accept(DeclaratorVisitor.INSTANCE));
    }

    private static final class DeclaratorVisitor extends Visitor<Modifier> {
        static final DeclaratorVisitor INSTANCE = new DeclaratorVisitor();

        boolean isToplevel(ParseTree cst) {
            return cst.getParent() instanceof TopLevelElementContext;
        }

        @Override
        public Modifier visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            if (isToplevel(ctx)) {
                return Modifier.IMPLICIT_STATIC;
            } else {
                return switch (CSTNodes.getTokenType(ctx.prefix)) {
                    case ZenScriptLexer.STATIC -> Modifier.STATIC;
                    case ZenScriptLexer.GLOBAL -> Modifier.GLOBAL;
                    default -> Modifier.NONE;
                };
            }
        }

        @Override
        public Modifier visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            return Modifier.EXPAND;
        }

        @Override
        public Modifier visitFormalParameter(FormalParameterContext ctx) {
            return Modifier.IMPLICIT_VAR;
        }

        @Override
        public Modifier visitClassDeclaration(ClassDeclarationContext ctx) {
            return Modifier.IMPLICIT_STATIC;
        }

        @Override
        public Modifier visitVariableDeclaration(VariableDeclarationContext ctx) {
            return switch (CSTNodes.getTokenType(ctx.prefix)) {
                case ZenScriptLexer.VAR -> Modifier.VAR;
                case ZenScriptLexer.VAL -> Modifier.VAL;
                case ZenScriptLexer.STATIC -> Modifier.STATIC;
                case ZenScriptLexer.GLOBAL -> Modifier.GLOBAL;
                default -> Modifier.NONE;
            };
        }

        @Override
        public Modifier visitForeachVariable(ForeachVariableContext ctx) {
            return Modifier.IMPLICIT_VAR;
        }
    }

}
