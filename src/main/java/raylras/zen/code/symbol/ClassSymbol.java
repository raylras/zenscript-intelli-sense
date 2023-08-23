package raylras.zen.code.symbol;

import raylras.zen.code.type.ClassType;

import java.util.List;

public interface ClassSymbol extends Symbol {

    String getQualifiedName();

    List<Symbol> getDeclaredMembers();

    List<Symbol> getMembers();

    List<ClassType> getInterfaces();

    @Override
    ClassType getType();

}
