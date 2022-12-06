package raylras.zen.semantic.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.semantic.scope.Scope;
import raylras.zen.semantic.type.Type;

import java.util.Objects;

public class VariableSymbol extends Symbol<Type> {

    private String varName;
    private int modifier;
    private Type type;

    public VariableSymbol(String name, Scope parentScope, ParseTree parseTree) {
        super(parentScope, parseTree);
        Objects.requireNonNull(name);
        this.varName = name;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    @Override
    public String getName() {
        return varName;
    }

    @Override
    public void setName(String name) {
        Objects.requireNonNull(name);
        this.varName = name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    @Override
    public String toString() {
        return "(symbol (variable " + varName + "))";
    }

    public static final class Modifier {
        public static final int VAR = 1;
        public static final int VAL = 2;
        public static final int STATIC = 3;
        public static final int GLOBAL = 4;
    }

}
