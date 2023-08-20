package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;

import java.util.Map;

public class ImportSymbol extends Symbol {

    public ImportSymbol(String name, ImportDeclarationContext cst, CompilationUnit unit) {
        super(name, cst, unit);
    }

    public Symbol getTarget() {
        Map<String, ClassType> classTypeMap = unit.getEnv().getClassTypeMap();
        ClassType type = classTypeMap.get(getCst().qualifiedName().getText());
        if (type != null) {
            return type.getSymbol();
        } else {
            return null;
        }
    }

    public String getQualifiedName() {
        return getCst().qualifiedName().getText();
    }

    @Override
    public Type getType() {
        Symbol target = getTarget();
        if (target != null) {
            return target.getType();
        } else {
            return null;
        }
    }

    @Override
    public Kind getKind() {
        return Kind.IMPORT;
    }

    @Override
    public ImportDeclarationContext getCst() {
        return (ImportDeclarationContext) cst;
    }

    @Override
    public String toString() {
        return getQualifiedName() + " as " + name;
    }

}
