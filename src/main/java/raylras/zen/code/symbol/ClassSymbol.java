package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ClassDeclarationContext;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.ClassType;

import java.util.Collections;
import java.util.List;

public class ClassSymbol extends Symbol {

    private final ClassType type;

    public ClassSymbol(ClassDeclarationContext owner, CompilationUnit unit) {
        super(owner, unit);
        this.type = new ClassType(this);
    }

    public List<Symbol> getMembers() {
        Scope scope = unit.getScope(owner);
        if (scope != null)
            return scope.getSymbols();
        return Collections.emptyList();
    }

    @Override
    public ClassType getType() {
        return type;
    }

    @Override
    public Kind getKind() {
        return Kind.CLASS;
    }

    @Override
    public ClassDeclarationContext getOwner() {
        return (ClassDeclarationContext) owner;
    }

}
