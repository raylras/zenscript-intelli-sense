package raylras.zen.code.symbol;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.type.ErrorType;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.resolve.NameResolver;
import raylras.zen.code.scope.Scope;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;
import raylras.zen.service.LibraryService;
import raylras.zen.util.StringUtils;
import raylras.zen.util.SymbolUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ClassSymbol extends Symbol {
    private final ClassType type;
    private final String qualifiedName;
    private final String simpleName;

    public ClassSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);

        String name = new NameResolver().resolve(owner);
        if (isLibrarySymbol()) {
            this.qualifiedName = name;
        } else {
            String packagePrefix = unit.relativePath();
            this.qualifiedName = packagePrefix + "." + name;
        }
        this.simpleName = StringUtils.getSimpleClassName(qualifiedName);
        this.type = new ClassType(qualifiedName, this);
    }

    @Override
    public String getName() {
        return this.simpleName;
    }

    public String getQualifiedName() {
        return this.qualifiedName;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    public boolean isFunctionalInterface() {
        return isLibrarySymbol() && getAnnotations().containsKey("function");
    }

    public List<ClassSymbol> getParents() {
        if (!isLibrarySymbol()) {
            return null;
        }

        String extendClasses = getAnnotations().get("extends");

        if (extendClasses == null) {
            return Collections.emptyList();
        }

        LibraryService libraryService = getUnit().libraryService();
        return Arrays.stream(extendClasses.split(","))
            .map(String::trim)
            .map(libraryService::getClassSymbol)
            .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getAnnotations() {
        return SymbolUtils.getAnnotations(getUnit(),
            ((ZenScriptParser.ClassDeclarationContext) getOwner()).ZEN_CLASS().getSymbol(),
            ImmutableSet.of("function", "extends")
        );
    }

    public FunctionType getFunctionType() {
        if (!isLibrarySymbol()) {
            return null;
        }
        String functionalInterface = getAnnotations().get("function");
        if (Strings.isNullOrEmpty(functionalInterface)) {
            return null;
        }

        Type type = SymbolUtils.parseTypeLiteral(functionalInterface, getUnit());
        if (type == null || type.getKind() != Type.Kind.FUNCTION) {
            return null;
        }
        return (FunctionType) type;
    }

    @Override
    public ZenSymbolKind getKind() {
        if (!this.isLibrarySymbol()) {
            return ZenSymbolKind.ZEN_CLASS;
        }
        if (SymbolUtils.isNativeClass(getName())) {
            return ZenSymbolKind.NATIVE_CLASS;
        }

        if (getMembers().stream().anyMatch(it -> it.getKind() == ZenSymbolKind.CONSTRUCTOR)) {
            return ZenSymbolKind.LIBRARY_CLASS;
        }

        if (isFunctionalInterface()) {
            return ZenSymbolKind.FUNCTIONAL_INTERFACE;
        }

        return ZenSymbolKind.INTERFACE;

    }

    @Override
    public List<Symbol> getMembers() {
        Scope scope = getUnit().getScope(getOwner());
        if (scope != null)
            return scope.symbols;
        return Collections.emptyList();
    }

}
