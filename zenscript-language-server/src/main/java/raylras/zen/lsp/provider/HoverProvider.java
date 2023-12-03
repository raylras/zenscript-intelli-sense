package raylras.zen.lsp.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import raylras.zen.lsp.bracket.BracketHandlerService;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.Visitor;
import raylras.zen.model.parser.ZenScriptParser.BracketHandlerExprContext;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Position;
import raylras.zen.util.Ranges;

import java.util.Deque;

public class HoverProvider {

    private HoverProvider() {}

    public static Hover hover(CompilationUnit unit, HoverParams params) {
        Position cursor = Position.of(params.getPosition());
        Deque<ParseTree> cstStack = CSTNodes.getCstStackAtPosition(unit.getParseTree(), cursor);
        HoverVisitor visitor = new HoverVisitor(BracketHandlerService.getInstance(unit.getEnv()));
        for (ParseTree cst : cstStack) {
            Hover hover = cst.accept(visitor);
            if (hover != null) {
                return hover;
            }
        }
        return null;
    }

    private static final class HoverVisitor extends Visitor<Hover> {

        private final BracketHandlerService service;

        private HoverVisitor(BracketHandlerService service) {
            this.service = service;
        }

        @Override
        public Hover visitBracketHandlerExpr(BracketHandlerExprContext ctx) {
            return service.getEntryRemote(ctx.raw().getText())
                    .map(entry -> {
                        StringBuilder builder = new StringBuilder();
                        entry.getFirst("_name").ifPresent(name -> {
                            builder.append("#### ");
                            builder.append(name);
                            builder.append("\n\n");
                        });
                        entry.getFirst("_icon").ifPresent(icon -> {
                            String img = "![img](data:image/png;base64," + icon + ")";
                            builder.append(img);
                            builder.append("\n\n");
                        });
                        Hover hover = toHover(builder.toString());
                        hover.setRange(Ranges.toLspRange(ctx));
                        return hover;
                    })
                    .orElse(null);
        }

        @Override
        public Hover visitChildren(RuleNode node) {
            return null;
        }

        private static Hover toHover(String text) {
            return new Hover(new MarkupContent(MarkupKind.MARKDOWN, text));
        }

        private static String toCodeBlock(String text) {
            return String.format("```zenscript\n%s\n```\n", text);
        }
    }

}
