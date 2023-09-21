package raylras.zen.model.symbol;

import raylras.zen.model.SymbolProvider;
import raylras.zen.model.type.ClassType;

import java.util.List;

public interface ClassSymbol extends Symbol, SymbolProvider {

    String getQualifiedName();

    List<Symbol> getDeclaredMembers();

    List<ClassType> getInterfaces();

    @Override
    ClassType getType();

}
