package raylras.zen.code.symbol;

import com.google.common.base.Strings;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.type.resolve.NameResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;
import raylras.zen.util.SymbolUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClassSymbol extends Symbol {

    public ClassSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
    }

    @Override
    public String getName() {
        return new NameResolver().resolve(getOwner());
    }

    @Override
    public Type getType() {
        String qualifiedName = new NameResolver().resolve(getOwner());
        return new ClassType(qualifiedName);
    }

    public List<Type> getCasters() {
        // TODO: Finish
        return Collections.emptyList();
    }

    public boolean isFunctionalInterface() {
        return getFunctionalInterface() != null;
    }

    public List<ClassSymbol> getParents() {
        if (!isLibrarySymbol()) {
            return null;
        }

        String extendClasses = getAnnotations().get("extends");

        if (extendClasses == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(extendClasses.split(","))
            .map(String::trim)
            .map(it -> getUnit().<ClassSymbol>lookupSymbol(it))
            .collect(Collectors.toList());
    }

    public FunctionSymbol getFunctionalInterface() {
        String functionalInterface = getAnnotations().get("function");
        if (Strings.isNullOrEmpty(functionalInterface)) {
            return null;
        }

        throw new IllegalStateException("not implemented");
    }

    @Override
    public ZenSymbolKind getKind() {
        if (!this.isLibrarySymbol()) {
            return ZenSymbolKind.ZEN_CLASS;
        }
        if (SymbolUtils.isNativeClass(getName())) {
            return ZenSymbolKind.NATIVE_CLASS;
        }

        if (getAnnotations().containsKey("interface")) {
            return ZenSymbolKind.INTERFACE;
        }

        if (getAnnotations().containsKey("function")) {
            return ZenSymbolKind.FUNCTIONAL_INTERFACE;
        }

        return ZenSymbolKind.LIBRARY_CLASS;
    }

    @Override
    public List<Symbol> getMembers() {
        Scope scope = getUnit().getScope(getOwner());
        if (scope != null)
            return scope.symbols;
        return Collections.emptyList();
    }

}
