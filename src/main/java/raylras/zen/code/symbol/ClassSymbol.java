package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.annotation.Annotation;
import raylras.zen.code.parser.ZenScriptParser.ClassDeclarationContext;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.ClassType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassSymbol extends Symbol {

    private final ClassType type;

    public ClassSymbol(String name, ClassDeclarationContext cst, CompilationUnit unit) {
        super(name, cst, unit);
        this.type = new ClassType(this);
    }

    public List<Symbol> getMembers() {
        List<Symbol> symbols = new ArrayList<>();
        Scope scope = unit.getScope(cst);
        if (scope != null) {
            symbols.addAll(scope.getSymbols());
        }
        for (ClassType anInterface : getInterfaces()) {
            symbols.addAll(anInterface.getMembers());
        }
        return symbols;
    }

    public List<ClassType> getInterfaces() {
        Map<String, ClassType> classTypeMap = unit.getEnv().getClassTypeMap();
        String[] interfaceNames = getDeclaredAnnotation("#extends").map(Annotation::getData).orElseGet(() -> new String[0]);

        List<ClassType> interfaces = new ArrayList<>();
        for (String name : interfaceNames) {
            ClassType type = classTypeMap.get(name);
            if (type != null) {
                interfaces.add(type);
            }
        }
        return interfaces;
    }

    public String getQualifiedName() {
        String declaredName = getSimpleName();
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
