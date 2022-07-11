package raylras.zen.ls.provider;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.*;
import raylras.zen.ast.decl.FunctionDeclaration;
import raylras.zen.ast.decl.ParameterDeclaration;
import raylras.zen.ast.decl.VariableDeclaration;
import raylras.zen.ast.expr.StringLiteral;
import raylras.zen.ast.expr.VarAccessExpression;
import raylras.zen.ast.stmt.VariableDeclStatement;
import raylras.zen.ast.type.Type;
import raylras.zen.ast.type.Types;
import raylras.zen.ast.visit.BaseVisitor;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HoverProvider {

    private static final class HoverVisitor extends BaseVisitor<Hover> {

        @Override
        public Hover visit(FunctionDeclaration funcDecl) {
            String name = funcDecl.getId().getName();
            String args = funcDecl.getParameters().stream()
                    .map(param -> param.getId().getName() + " as " +  param.getType())
                    .collect(Collectors.joining(", "));
            Type result = funcDecl.getResultDecl().map(BaseNode::getType).orElse(Types.ANY);
            String content = formatted("function %s(%s) as %s", name, args, result);
            return new Hover(new MarkupContent(MarkupKind.MARKDOWN, content), funcDecl.getId().getRange().toLSPRange());
        }

        @Override
        public Hover visit(VarAccessExpression varAccess) {
            return Optional.ofNullable(varAccess.getSymbol())
                    .map(Symbol::node)
                    .map(node -> node.accept(this))
                    .map(hover1 -> {
                        hover1.setRange(varAccess.getRange().toLSPRange());
                        return hover1;
                    })
                    .orElse(null);
        }

        @Override
        public Hover visit(VariableDeclaration varDecl) {
            String content = formatted("(local) %s as %s", varDecl.getId().getName(), varDecl.getType());
            return new Hover(new MarkupContent(MarkupKind.MARKDOWN, content), varDecl.getId().getRange().toLSPRange());
        }

        @Override
        public Hover visit(VariableDeclStatement varDeclStmt) {
            String modifier;
            if (varDeclStmt.isGlobal()) {
                modifier = "global";
            } else if (varDeclStmt.isStatic()) {
                modifier = "static";
            } else if (varDeclStmt.isFinal()) {
                modifier = "val";
            } else {
                modifier = "var";
            }
            String name = varDeclStmt.getId().getName();
            Type type = Optional.ofNullable(varDeclStmt.getType()).orElse(Types.ANY);
            String content = formatted("%s %s as %s", modifier, name, type);
            return new Hover(new MarkupContent(MarkupKind.MARKDOWN, content), varDeclStmt.getId().getRange().toLSPRange());
        }

        @Override
        public Hover visit(ParameterDeclaration paramDecl) {
            String content = formatted("(parameter) %s", paramDecl.toString());
            return new Hover(new MarkupContent(MarkupKind.MARKDOWN, content), paramDecl.getId().getRange().toLSPRange());
        }

        private static final Pattern UNICODE_PATTERN = Pattern.compile("\\\\u([0-9a-fA-F]{4})");

        @Override
        public Hover visit(StringLiteral stringExpr) {
            Matcher matcher = UNICODE_PATTERN.matcher(stringExpr.getLiteral());
            if (matcher.find()) {
                // Unicode to String
                String content = matcher.replaceAll(r -> String.valueOf((char) Integer.parseInt(r.group(1), 16)));
                return new Hover(new MarkupContent(MarkupKind.MARKDOWN, content), stringExpr.getRange().toLSPRange());
            }
            return null;
        }

    }

    public Hover provideHover(@NotNull HoverParams params, @NotNull CompileUnit compileUnit) {
        ScriptNode scriptNode = compileUnit.getScriptNode(params.getTextDocument().getUri());
        List<Node> nodeAtPosition = scriptNode.getNodeAtPosition(Position.of(params.getPosition()));
        return nodeAtPosition.stream()
                .filter(node -> !(node instanceof IDNode))
                .findFirst()
                .map(node -> node.accept(new HoverVisitor()))
                .orElse(null);
    }

    private static String formatted(String format, Object... args) {
        // template
        return """
                ```zenscript
                %s
                ```""".formatted(format).formatted(args);
    }

}
