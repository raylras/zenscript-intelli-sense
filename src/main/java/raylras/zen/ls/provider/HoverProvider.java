package raylras.zen.ls.provider;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.*;
import raylras.zen.ast.decl.FunctionDeclaration;
import raylras.zen.ast.expr.VarAccessExpression;
import raylras.zen.ast.type.Type;
import raylras.zen.ast.type.Types;
import raylras.zen.ast.visit.BaseVisitor;

import java.util.List;
import java.util.Optional;
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
            String content = """
            ```zenscript
            function %s(%s) as %s
            ```
            """.formatted(name, args, result);
            return new Hover(new MarkupContent(MarkupKind.MARKDOWN, content), funcDecl.getId().getRange().toLSPRange());
        }

        @Override
        public Hover visit(VarAccessExpression varAccess) {
            Optional<Hover> hover = varAccess.getSymbol()
                    .map(Symbol::node)
                    .map(node -> node.accept(this))
                    .map(hover1 -> {
                        hover1.setRange(varAccess.getRange().toLSPRange());
                        return hover1;
                    });
            return hover.orElse(null);
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
