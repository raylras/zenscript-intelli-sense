package raylras.zen.code.type;

import raylras.zen.code.symbol.ClassSymbol;

import java.util.List;

public class ClassType extends Type {

    public ClassSymbol symbol;
    public List<Type> superType;
    public List<Type> interfaces;

    @Override
    public Tag getTag() {
        return Tag.CLASS;
    }

}
