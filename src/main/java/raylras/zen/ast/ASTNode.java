package raylras.zen.ast;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public abstract class ASTNode {

    // source position
    private int line = -1;
    private int column = -1;
    private int lastLine = -1;
    private int lastColumn = -1;

    public void accept(ASTVisitor<?> visitor) {}

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getLastLine() {
        return lastLine;
    }

    public void setLastLine(int lastLine) {
        this.lastLine = lastLine;
    }

    public int getLastColumn() {
        return lastColumn;
    }

    public void setLastColumn(int lastColumn) {
        this.lastColumn = lastColumn;
    }


    // set source position from antlr
    public <T extends ASTNode> T setSourcePosition(ParserRuleContext ctx) {
        this.setLine(ctx.start.getLine());
        this.setColumn(ctx.start.getCharPositionInLine());
        this.setLastLine(ctx.stop.getLine());
        this.setLastColumn(ctx.stop.getCharPositionInLine());
        return (T) this;
    }

    // set source position from antlr
    public <T extends ASTNode> T setSourcePosition(Token token) {
        this.setLine(token.getLine());
        this.setColumn(token.getCharPositionInLine());
        this.setLastLine(token.getLine());
        this.setLastColumn(token.getCharPositionInLine() + token.getText().length());
        return (T) this;
    }

    // set source position from another ASTNode
    public <T extends ASTNode> T setSourcePosition(ASTNode node) {
        this.setLine(node.getLine());
        this.setColumn(node.getColumn());
        this.setLastLine(node.getLastLine());
        this.setLastColumn(node.getLastColumn());
        return (T) this;
    }

}
