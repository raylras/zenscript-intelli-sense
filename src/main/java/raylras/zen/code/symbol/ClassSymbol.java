package raylras.zen.code.symbol;

import raylras.zen.code.common.MemberProvider;
import raylras.zen.code.type.ClassType;

import java.util.List;

public interface ClassSymbol extends Symbol, MemberProvider {

    String getQualifiedName();

    List<Symbol> getDeclaredMembers();

    @Override
    List<Symbol> getMembers();

    List<ClassType> getInterfaces();

    @Override
    ClassType getType();

}
