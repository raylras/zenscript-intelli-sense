package raylras.zen.langserver.provider;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.BracketHandlerExprContext;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;
import youyihj.probezs.api.BracketHandlerResult;
import youyihj.probezs.api.BracketHandlerService;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
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
            Map<String, String> properties = query(ctx.raw().getText());
            StringBuilder builder = new StringBuilder();
            properties.computeIfPresent("name", (key, name) -> {
                builder.append("#### ");
                builder.append(name);
                builder.append("\n\n");
                return null;
            });
            properties.computeIfPresent("icon", (key, icon) -> {
                // String apple = "iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAABbElEQVRYCe2VMUrEQBiFUyiyxQqyq4siAVFBsLHQG3gDt97eE2zlNewUKwsbQbyCJ7D2Dt7gyR/9ZPePy2QmIUnhwuPtTGb+/703E5Jl/7+GEpifD2VoqFx8mc4E0Pgy35AhXnrNHb0RUNNH+nYSSK8QufN951CGl1Fe4Gx7TYbW7kDrAmgIf+6eyDA9GizhdZTLcDMcLyEy4PJyGsOtCfANtX+qv4BznjGGSaRsLTDTGwE409OzCtzeSYbZ9TcCydROoHUBpegfHiVDRecI/pgcyxB9F3ojACe6mqkACaw4c9bzmvoEpoNNGQJ3P8tIgILdCwg4/hX6s675BNoWwFfOOwuNvfO38YEMvAWV70DnAril91t7MnCbfQI4hlmX7JzGcG8EcIYwTj1756yvfPY4h0mAQrBvzLhxAQjBAQLgi8m6FsE8zD7qJDOFKAwvNrf/zMPsS27sN1IQjn3u10ePaQz7AszD/vmq8RcMHK4WKqku/QAAAABJRU5ErkJggg==";
                String img = "![img](data:image/png;base64," + icon + ")";
                builder.append(img);
                builder.append("\n\n");
                return null;
            });
            return toHover(builder.toString());
        }

        private static Map<String, String> query(String raw) {
            try (Socket socket = new Socket("127.0.0.1", 6489)) {
                JsonRpcClient client = new JsonRpcClient();
                client.getObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                BracketHandlerService service = ProxyUtil.createClientProxy(BracketHandlerService.class.getClassLoader(), BracketHandlerService.class, client, socket);
                BracketHandlerResult result = service.query(raw, true);
                Map<String, String> properties = new HashMap<>(result.getExtras());
                properties.put("type", result.getType());
                return properties;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return Collections.emptyMap();
            }
        }

        private static Hover toHover(String text) {
            return new Hover(new MarkupContent(MarkupKind.MARKDOWN, text));
        }

        private static String toCodeBlock(String text) {
            return String.format("```zenscript\n%s\n```\n", text);
        }
    }

}
