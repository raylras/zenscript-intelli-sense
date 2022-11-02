package raylras.zen.format;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import raylras.zen.cst.ZenScriptLexer;
import raylras.zen.cst.ZenScriptParser;
import raylras.zen.cst.ZenScriptParserBaseListener;

import java.util.Optional;

public class Formatter extends ZenScriptParserBaseListener {

    private final TokenStreamRewriter rewriter;
    private final CommonTokenStream tokenStream;

    public Formatter(CommonTokenStream tokenStream) {
        this.tokenStream = tokenStream;
        this.rewriter = new TokenStreamRewriter(tokenStream);
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

    @Override
    public void enterVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        Optional.ofNullable(tokenStream.getHiddenTokensToLeft(ctx.IDENTIFIER().getSymbol().getTokenIndex(), ZenScriptLexer.WHITE_SPACE_CHANNEL))
                .map(tokens -> tokens.get(0))
                .ifPresent(blank -> rewriter.replace(blank, " "));
        Optional.ofNullable(tokenStream.getHiddenTokensToRight(ctx.IDENTIFIER().getSymbol().getTokenIndex(), ZenScriptLexer.WHITE_SPACE_CHANNEL))
                .map(tokens -> tokens.get(0))
                .ifPresent(blank -> rewriter.replace(blank, " "));
        if (ctx.AS() != null) {
            Optional.ofNullable(tokenStream.getHiddenTokensToRight(ctx.AS().getSymbol().getTokenIndex(), ZenScriptLexer.WHITE_SPACE_CHANNEL))
                    .map(tokens -> tokens.get(0))
                    .filter(blank ->  blank.getText().length() > 1)
                    .ifPresent(blank -> rewriter.replace(blank, " "));
        }
        if (ctx.ASSIGN() != null) {
            Optional.ofNullable(tokenStream.getHiddenTokensToLeft(ctx.ASSIGN().getSymbol().getTokenIndex(), ZenScriptLexer.WHITE_SPACE_CHANNEL))
                    .map(tokens -> tokens.get(0))
                    .ifPresentOrElse(blank -> rewriter.replace(blank, " "), () -> rewriter.insertBefore(ctx.ASSIGN().getSymbol().getTokenIndex(), " "));
            Optional.ofNullable(tokenStream.getHiddenTokensToRight(ctx.ASSIGN().getSymbol().getTokenIndex(), ZenScriptLexer.WHITE_SPACE_CHANNEL))
                    .map(tokens -> tokens.get(0))
                    .ifPresentOrElse(blank -> rewriter.replace(blank, " "), () -> rewriter.insertAfter(ctx.ASSIGN().getSymbol().getTokenIndex(), " "));
        }
        Optional.ofNullable(tokenStream.getHiddenTokensToLeft(ctx.SEMICOLON().getSymbol().getTokenIndex(), ZenScriptLexer.WHITE_SPACE_CHANNEL))
                .map(tokens -> tokens.get(0))
                .ifPresent(blank -> rewriter.delete(blank));
        Optional.ofNullable(tokenStream.getHiddenTokensToRight(ctx.SEMICOLON().getSymbol().getTokenIndex()))
                .map(tokens -> tokens.get(0))
                .ifPresentOrElse(blank -> rewriter.replace(blank, "\n"), () -> rewriter.insertAfter(ctx.SEMICOLON().getSymbol().getTokenIndex(), "\n"));
        super.enterVariableDeclaration(ctx);
    }


    @Override
    public void exitVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
        super.exitVariableDeclaration(ctx);
    }

}
