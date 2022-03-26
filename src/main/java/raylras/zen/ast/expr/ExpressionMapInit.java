package raylras.zen.ast.expr;

import java.util.List;

public class ExpressionMapInit extends Expression {

    private List<ExpressionMapEntry> entries;

    public List<ExpressionMapEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ExpressionMapEntry> entries) {
        this.entries = entries;
    }

}
