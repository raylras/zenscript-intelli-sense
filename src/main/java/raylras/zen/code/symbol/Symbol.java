package raylras.zen.code.symbol;

import raylras.zen.code.type.Type;

public abstract class Symbol {

    public String name;
    public Type type;
    public Symbol owner;

    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitSymbol(this);
    }

    public interface Visitor<R> {
        R visitClassSymbol(ClassSymbol symbol);
        R visitFunctionSymbol(FunctionSymbol symbol);
        R visitVariableSymbol(VariableSymbol symbol);
        R visitSymbol(Symbol symbol);
    }

}
