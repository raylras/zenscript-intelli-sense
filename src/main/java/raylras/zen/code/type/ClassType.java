package raylras.zen.code.type;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.symbol.ClassSymbol;

public class ClassType extends Type {

    public String name;
    private CompilationUnit unit;

    public ClassType(String name, CompilationUnit unit) {
        this.unit = unit;
    }

    public ClassSymbol getSymbol() {
        return null;
    }

    @Override
    public Kind getKind() {
        return Kind.CLASS;
    }

    @Override
    public String toString() {
        ClassSymbol symbol = getSymbol();
        if (symbol != null) {
            return symbol.getName();
        }
        return super.toString();
    }

}
