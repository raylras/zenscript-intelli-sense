package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.Type;

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
    public ImportDeclarationContext getOwner() {
        return (ImportDeclarationContext) owner;
    }

}
