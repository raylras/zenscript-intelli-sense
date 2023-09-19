package raylras.zen.code.symbol;

import raylras.zen.code.SymbolProvider;
import raylras.zen.code.type.ClassType;

import java.util.List;

public interface ClassSymbol extends Symbol, SymbolProvider {

    String getQualifiedName();

    List<Symbol> getDeclaredMembers();

    List<ClassType> getInterfaces();

    @Override
    ClassType getType();

}
