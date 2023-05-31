package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ClassDeclarationContext;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;

public class ClassSymbol extends Symbol {

    public ClassSymbol(ClassDeclarationContext owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public Type getType() {
        return AnyType.INSTANCE;
    }

    @Override
    public Kind getKind() {
        return Kind.CLASS;
    }

    @Override
    public List<Symbol> getMembers() {
        Scope scope = unit.getScope(owner);
        if (scope != null)
            return scope.getSymbols();
        return Collections.emptyList();
    }

    @Override
    public ClassDeclarationContext getOwner() {
        return (ClassDeclarationContext) owner;
    }

}
