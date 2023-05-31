package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;

public class ImportSymbol extends Symbol {

    public ImportSymbol(ImportDeclarationContext owner, CompilationUnit unit) {
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
        return Collections.emptyList();
    }

    @Override
    public ImportDeclarationContext getOwner() {
        return (ImportDeclarationContext) owner;
    }

}
