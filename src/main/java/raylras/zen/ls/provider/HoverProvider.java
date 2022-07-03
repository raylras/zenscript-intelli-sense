package raylras.zen.ls.provider;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.*;
import raylras.zen.ast.decl.FunctionDeclaration;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.type.Types;
import raylras.zen.ast.visit.BaseVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class HoverProvider {

    private static final class HoverVisitor extends BaseVisitor<Hover> {

        @Override
        public Hover visit(FunctionDeclaration funcDecl) {
            StringBuilder sb = new StringBuilder();
            sb.append("function ");
            sb.append(funcDecl.getId().getName());
            sb.append("(");
            sb.append(funcDecl.getParameters().stream()
                    .map(param -> param.getId().getName()
                            + " as " + (param.getType() == null ? "any" : param.getType()))
                    .collect(Collectors.joining(", "))
            );
            sb.append(")");
            sb.append(" as ");
            sb.append(funcDecl.getResultDecl().map(BaseNode::getType).orElse(Types.ANY));
            Hover hover = new Hover(new MarkupContent(MarkupKind.MARKDOWN, sb.toString()));
            hover.setRange(funcDecl.getId().getRange().toLSPRange());
            return hover;
        }

        @Override
        public Hover visit(Expression expr) {
            Hover hover = new Hover(new MarkupContent(MarkupKind.MARKDOWN, expr.toString()));
            hover.setRange(expr.getRange().toLSPRange());
            return hover;
        }

    }

    public Hover provideHover(@NotNull HoverParams params, @NotNull CompileUnit compileUnit) {
        ScriptNode scriptNode = compileUnit.getScriptNode(params.getTextDocument().getUri());
        List<Node> nodeAtPosition = scriptNode.getNodeAtPosition(Position.of(params.getPosition()));
        for (Node node : nodeAtPosition) {
            if (node instanceof IDNode) continue;
            return node.accept(new HoverVisitor());
        }
        return null;
    }

}
