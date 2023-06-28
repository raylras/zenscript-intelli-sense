package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.annotation.Annotation;
import raylras.zen.code.parser.ZenScriptParser.ClassDeclarationContext;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.ClassType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ClassSymbol extends Symbol {

    private final ClassType type;

    public ClassSymbol(ClassDeclarationContext owner, CompilationUnit unit) {
        super(owner, unit);
        this.type = new ClassType(this);
    }

    public List<Symbol> getMembers() {
        Scope scope = unit.getScope(owner);
        if (scope != null) {
            return scope.getSymbols();
        } else {
            return Collections.emptyList();
        }
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

    @Override
    public ClassType getType() {
        return type;
    }

    @Override
    public Kind getKind() {
        return Kind.CLASS;
    }

    @Override
    public String getFullyQualifiedName() {
        if (unit.isDzs()) {
            return getDeclaredName();
        }

        // TODO: fully qualified name under relative path
        return getDeclaredName();
    }

    @Override
    public ClassDeclarationContext getOwner() {
        return (ClassDeclarationContext) owner;
    }

}
