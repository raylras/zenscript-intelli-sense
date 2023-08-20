package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.ClassDeclarationContext;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.ClassType;
import raylras.zen.util.CSTNodes;

import java.util.*;
import java.util.stream.Collectors;

public class ClassSymbol extends Symbol {

    private final ClassType type;

    public ClassSymbol(String name, ClassDeclarationContext cst, CompilationUnit unit) {
        super(name, cst, unit);
        this.type = new ClassType(this);
    }

    public List<Symbol> getDeclaredMembers() {
        Scope scope = unit.getScope(cst);
        if (scope != null) {
            return scope.getSymbols();
        } else {
            return Collections.emptyList();
        }
    }

    public List<Symbol> getMembers() {
        List<Symbol> symbols = new ArrayList<>(getDeclaredMembers());
        for (ClassType superClass : getInterfaces()) {
            symbols.addAll(superClass.getMembers());
        }
        return symbols;
    }

    public List<ClassType> getInterfaces() {
        if (getCst().qualifiedNameList() == null) {
            return Collections.emptyList();
        }
        Map<String, ClassType> classTypeMap = unit.getEnv().getClassTypeMap();
        Scope scope = unit.lookupScope(cst);
        return getCst().qualifiedNameList().qualifiedName().stream()
                .map(CSTNodes::getText)
                .map(name -> scope.lookupSymbol(ImportSymbol.class, name))
                .filter(Objects::nonNull)
                .map(ImportSymbol::getQualifiedName)
                .map(classTypeMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public String getQualifiedName() {
        String declaredName = getName();
        String packageName = unit.getPackage();
        return packageName.isEmpty() ? declaredName : packageName + "." + declaredName;
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
    public String getNameWithType() {
        return getQualifiedName();
    }

    @Override
    public ClassDeclarationContext getCst() {
        return (ClassDeclarationContext) cst;
    }

}
