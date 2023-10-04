package raylras.zen.model.symbol;

import raylras.zen.model.type.ClassType;

import java.util.List;

public interface ClassSymbol extends Symbol, SymbolProvider {

    String getQualifiedName();

    String getSimpleName();

    List<Symbol> getDeclaredMembers();

    List<ClassSymbol> getInterfaces();

    @Override
    ClassType getType();

    @Override
    List<Symbol> getSymbols();

}
