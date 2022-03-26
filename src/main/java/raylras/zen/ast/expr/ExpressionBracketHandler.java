package raylras.zen.ast.expr;

public class ExpressionBracketHandler extends Expression {

    private String content;

    public String getHead() {
        return content.substring(1, content.indexOf(':')).trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
