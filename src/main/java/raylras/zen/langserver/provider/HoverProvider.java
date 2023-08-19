package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.BracketHandlerExprContext;
import raylras.zen.rpc.RpcClient;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.MapUtils;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

import java.util.Deque;
import java.util.Map;

public class HoverProvider {

    public static Hover hover(CompilationUnit unit, HoverParams params) {
        Range cursor = Ranges.of(params.getPosition());
        Deque<ParseTree> cstStack = CSTNodes.getCstStackAtLineAndColumn(unit.getParseTree(), cursor.startLine, cursor.startColumn);
        HoverVisitor visitor = new HoverVisitor();
        for (ParseTree cst : cstStack) {
            Hover hover = cst.accept(visitor);
            if (hover != null) {
                return hover;
            }
        }
        return null;
    }

    private static final class HoverVisitor extends Visitor<Hover> {

        @Override
        public Hover visitBracketHandlerExpr(BracketHandlerExprContext ctx) {
            Map<String, String> properties = RpcClient.queryBracketHandler(ctx.raw().getText());
            StringBuilder builder = new StringBuilder();
            MapUtils.ifPresent(properties, "name", name -> {
                builder.append("#### ");
                builder.append(name);
                builder.append("\n\n");
            });
            MapUtils.ifPresent(properties, "icon", icon -> {
                String img = "![img](data:image/png;base64," + icon + ")";
                builder.append(img);
                builder.append("\n\n");
            });
            Hover hover = toHover(builder.toString());
            hover.setRange(Ranges.toLSPRange(ctx));
            return hover;
        }

        private static Hover toHover(String text) {
            return new Hover(new MarkupContent(MarkupKind.MARKDOWN, text));
        }

        private static String toCodeBlock(String text) {
            return String.format("```zenscript\n%s\n```\n", text);
        }
    }

}
