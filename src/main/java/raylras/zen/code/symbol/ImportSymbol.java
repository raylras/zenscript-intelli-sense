package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;

public class ImportSymbol extends Symbol {

    public ImportSymbol(ImportDeclarationContext owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public String getSimpleName() {
        return getQualifiedName();
    }

    @Override
    public String getQualifiedName() {
        if (owner == null)
            return "";
        return new NameResolver().resolve(getOwner().qualifiedName());
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
        return Collections.emptyList();
    }

    @Override
    public ImportDeclarationContext getOwner() {
        return (ImportDeclarationContext) owner;
    }

}
