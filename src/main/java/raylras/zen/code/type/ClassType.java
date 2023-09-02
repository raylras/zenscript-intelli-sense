package raylras.zen.code.type;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.MemberProvider;
import raylras.zen.code.symbol.ClassSymbol;
import raylras.zen.code.symbol.Symbol;

import java.util.List;

public class ClassType extends Type implements MemberProvider {

    private final ClassSymbol symbol;

    public ClassType(ClassSymbol symbol) {
        this.symbol = symbol;
    }

    public ClassSymbol getSymbol() {
        return symbol;
    }

    @Override
    public List<Symbol> getMembers() {
        return symbol.getMembers();
    }

    @Override
    public SubtypeResult isSubtypeOf(Type type, CompilationEnvironment env) {
        if (this.equals(type)) {
            return SubtypeResult.SELF;
        }
        if (type instanceof ClassType) {
            boolean matchedInterface = symbol.getInterfaces().stream()
                    .flatMap(classType -> classType.getSymbol().getInterfaces().stream())
                    .anyMatch(classType -> classType.isSubtypeOf(type, env).matched());
            if (matchedInterface) {
                return SubtypeResult.INHERIT;
            }
        }
        return super.isSubtypeOf(type, env);
    }

    @Override
    public String toString() {
        return symbol.getName();
    }

}
