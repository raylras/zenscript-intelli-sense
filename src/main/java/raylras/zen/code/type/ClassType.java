package raylras.zen.code.type;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.StringUtils;

public class ClassType extends NamedType {
    private final ClassSymbol classSymbol;

    private final String qualifiedName;

    public ClassType(String qualifiedName, ClassSymbol classSymbol) {
        super(StringUtils.getSimpleClassName(qualifiedName));
        this.qualifiedName = qualifiedName;
        this.classSymbol = classSymbol;
    }


    public String getQualifiedName() {
        return qualifiedName;
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
