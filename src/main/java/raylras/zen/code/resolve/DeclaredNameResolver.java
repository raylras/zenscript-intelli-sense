package raylras.zen.code.resolve;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;

import java.util.List;
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
                List<SimpleNameContext> simpleNameList = ctx.qualifiedName().simpleName();
                SimpleNameContext lastSimpleName = simpleNameList.get(simpleNameList.size() - 1);
                return getText(lastSimpleName);
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
        public String visitSimpleName(SimpleNameContext ctx) {
            return getText(ctx);
        }

        @Override
        public String visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            return getText(ctx.simpleName());
        }

        @Override
        public String visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            return getText(ctx.simpleName());
        }

        @Override
        public String visitFormalParameter(FormalParameterContext ctx) {
            return getText(ctx.simpleName());
        }

        @Override
        public String visitClassDeclaration(ClassDeclarationContext ctx) {
            return getText(ctx.simpleNameOrPrimitiveType());
        }

        @Override
        public String visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            return getText(ctx.ZEN_CONSTRUCTOR());
        }

        @Override
        public String visitVariableDeclaration(VariableDeclarationContext ctx) {
            return getText(ctx.simpleName());
        }

        @Override
        public String visitForeachVariable(ForeachVariableContext ctx) {
            return getText(ctx.simpleName());
        }

        @Override
        public String visitSimpleNameExpr(SimpleNameExprContext ctx) {
            return getText(ctx.simpleName());
        }
    }

}
