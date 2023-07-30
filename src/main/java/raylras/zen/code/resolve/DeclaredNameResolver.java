package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;

import java.util.Objects;

public final class DeclaredNameResolver {

    private DeclaredNameResolver() {}

    public static String getDeclaredName(ParseTree cst) {
        Objects.requireNonNull(cst);
        return cst.accept(DeclaredNameVisitor.INSTANCE);
    }

    private static final class DeclaredNameVisitor extends Visitor<String> {
        private static final DeclaredNameVisitor INSTANCE = new DeclaredNameVisitor();

        private static String getText(ParseTree cst) {
            if (cst != null) {
                return cst.getText();
            } else {
                return null;
            }
        }

        @Override
        public String visitTerminal(TerminalNode node) {
            return getText(node);
        }

        @Override
        public String visitImportDeclaration(ImportDeclarationContext ctx) {
            if (ctx.alias() != null) {
                return getText(ctx.alias());
            } else {
                return getText(ctx.qualifiedName().identifier());
            }
        }

        @Override
        public String visitQualifiedName(QualifiedNameContext ctx) {
            return getText(ctx);
        }

        @Override
        public String visitAlias(AliasContext ctx) {
            return getText(ctx);
        }

        @Override
        public String visitIdentifier(IdentifierContext ctx) {
            return getText(ctx);
        }

        @Override
        public String visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            return getText(ctx.identifier());
        }

        @Override
        public String visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            return getText(ctx.identifier());
        }

        @Override
        public String visitParameter(ParameterContext ctx) {
            return getText(ctx.identifier());
        }

        @Override
        public String visitClassDeclaration(ClassDeclarationContext ctx) {
            return getText(ctx.qualifiedName());
        }

        @Override
        public String visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            return getText(ctx.ZEN_CONSTRUCTOR());
        }

        @Override
        public String visitVariableDeclaration(VariableDeclarationContext ctx) {
            return getText(ctx.identifier());
        }

        @Override
        public String visitForeachVariableDeclaration(ForeachVariableDeclarationContext ctx) {
            return getText(ctx.identifier());
        }

        @Override
        public String visitLocalAccessExpr(LocalAccessExprContext ctx) {
            return getText(ctx.identifier());
        }
    }

}
