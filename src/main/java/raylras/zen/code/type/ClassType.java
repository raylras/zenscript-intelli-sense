package raylras.zen.code.type;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;

public class ClassType extends NamedType {
    private final ClassSymbol classSymbol;

    public ClassType(String qualifiedName, ClassSymbol classSymbol) {
        super(qualifiedName);
        this.classSymbol = classSymbol;
    }

    public ClassSymbol getSymbol() {
        return classSymbol;
    }

    public boolean isLibraryClass() {
        return classSymbol.isLibrarySymbol();
    }

    @Override
    public Kind getKind() {
        return Kind.CLASS;
    }


}
