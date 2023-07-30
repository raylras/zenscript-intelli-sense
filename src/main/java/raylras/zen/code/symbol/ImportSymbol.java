package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ImportDeclarationContext;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;

import java.util.Map;

public class ImportSymbol extends Symbol {

    public ImportSymbol(ImportDeclarationContext cst, CompilationUnit unit) {
        super(cst, unit);
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
    public String getQualifiedName() {
        // TODO: getQualifiedName
        throw new RuntimeException("TODO");
    }

    @Override
    public ImportDeclarationContext getCst() {
        return (ImportDeclarationContext) cst;
    }

}
