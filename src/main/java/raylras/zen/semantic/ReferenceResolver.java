package raylras.zen.semantic;

import raylras.zen.cst.ZenScriptParser;
import raylras.zen.cst.ZenScriptParserBaseListener;
import raylras.zen.semantic.symbol.Symbol;

/**
 * The third scan, records all references to defined symbols to the annotated tree.
 */
public class ReferenceResolver extends ZenScriptParserBaseListener {

    private final AnnotatedTree annotatedTree;

    public ReferenceResolver(AnnotatedTree annotatedTree) {
        this.annotatedTree = annotatedTree;
    }

    @Override
    public void enterIDExpression(ZenScriptParser.IDExpressionContext ctx) {
        Symbol<?> symbol = annotatedTree.findSymbolOfNode(ctx.IDENTIFIER(), ctx.IDENTIFIER().getText());
        if (symbol!= null) {
            annotatedTree.bindNodeToSymbol(ctx.IDENTIFIER(), symbol);
        }
    }

}
