package raylras.zen.format;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import raylras.zen.cst.ZenScriptParser;
import raylras.zen.cst.ZenScriptParserBaseListener;

public class Formatter extends ZenScriptParserBaseListener {

    private final TokenStreamRewriter rewriter;
    private final CommonTokenStream tokens;

    public Formatter(CommonTokenStream tokens) {
        this.tokens = tokens;
        this.rewriter = new TokenStreamRewriter(tokens);
    }

    public String getText() {
        return rewriter.getText();
    }

    @Override
    public void enterCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        super.enterCompilationUnit(ctx);
    }

    @Override
    public void exitCompilationUnit(ZenScriptParser.CompilationUnitContext ctx) {
        super.exitCompilationUnit(ctx);
    }

}
