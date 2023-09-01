package raylras.zen.code.symbol;

import raylras.zen.code.MemberProvider;

import java.util.List;

public interface PackageSymbol extends Symbol, MemberProvider {

    String getQualifiedName();

    @Override
    List<Symbol> getMembers();
}
