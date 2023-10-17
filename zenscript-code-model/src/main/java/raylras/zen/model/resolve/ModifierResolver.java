package raylras.zen.model.resolve;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.model.Visitor;
import raylras.zen.model.symbol.Symbol.Modifier;
import raylras.zen.util.CSTNodes;

import java.util.Optional;

import static raylras.zen.model.parser.ZenScriptParser.*;

public final class ModifierResolver {

    private ModifierResolver() {}

    public static Optional<Modifier> getModifier(ParseTree cst) {
        return Optional.ofNullable(cst)
                .map(ModifierVisitor.INSTANCE::visit);
    }

    private static Modifier toModifier(Token token) {
        return switch (CSTNodes.getTokenType(token)) {
            case VAR -> Modifier.VAR;
            case VAL -> Modifier.VAL;
            case STATIC -> Modifier.STATIC;
            case GLOBAL -> Modifier.GLOBAL;
            case EXPAND -> Modifier.EXPAND;
            default -> Modifier.NONE;
        };
    }

    private static final class ModifierVisitor extends Visitor<Modifier> {
        static final ModifierVisitor INSTANCE = new ModifierVisitor();

        boolean isToplevel(ParseTree cst) {
            return cst.getParent() instanceof TopLevelElementContext;
        }

        @Override
        public Modifier visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            if (ctx.prefix != null) {
                return toModifier(ctx.prefix);
            }
            if (isToplevel(ctx)) {
                return Modifier.IMPLICIT_STATIC;
            }
            return Modifier.NONE;
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
            return toModifier(ctx.prefix);
        }

        @Override
        public Modifier visitForeachVariable(ForeachVariableContext ctx) {
            return Modifier.IMPLICIT_VAR;
        }
    }

}
