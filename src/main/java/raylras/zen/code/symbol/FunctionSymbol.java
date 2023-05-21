package raylras.zen.code.symbol;

import com.google.common.collect.ImmutableSet;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.type.resolve.NameResolver;
import raylras.zen.code.type.resolve.ParamsResolver;
import raylras.zen.code.type.resolve.ReturnTypeResolver;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;
import raylras.zen.util.SymbolUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FunctionSymbol extends Symbol {

    private final boolean isConstructor;


    public FunctionSymbol(ParseTree owner, CompilationUnit unit, boolean isConstructor) {
        super(owner, unit);
        this.isConstructor = isConstructor;
    }


    @Override
    public Map<String, String> getAnnotations() {
        Token locator;
        ParseTree owner = getOwner();

        if (owner instanceof ZenScriptParser.FunctionDeclarationContext) {
            locator = ((ZenScriptParser.FunctionDeclarationContext) owner).FUNCTION().getSymbol();
        } else if (owner instanceof ZenScriptParser.ExpandFunctionDeclarationContext) {
            locator = ((ZenScriptParser.ExpandFunctionDeclarationContext) owner).DOLLAR().getSymbol();
        } else if (owner instanceof ZenScriptParser.ConstructorDeclarationContext) {
            locator = ((ZenScriptParser.ConstructorDeclarationContext) owner).ZEN_CONSTRUCTOR().getSymbol();
        } else {
            return Collections.emptyMap();
        }

        return SymbolUtils.getAnnotations(getUnit(),
            locator,
            ImmutableSet.of("caster", "operator", "hidden", "varargs")
        );
    }


    public boolean isVarargs() {
        return isLibrarySymbol() && getAnnotations().containsKey("varargs");
    }

    public OperatorType getOperatorType() {
        if (!isLibrarySymbol()) {
            return OperatorType.NOT_OPERATOR;
        }

        Map<String, String> annotations = getAnnotations();
        if (annotations.containsKey("caster")) {
            return OperatorType.CASTER;
        }
        return OperatorType.fromString(annotations.get("operator"));
    }

    public boolean isHidden() {
        return getAnnotations().containsKey("hidden");
    }

    @Override
    public String getName() {
        return new NameResolver().resolve(getOwner());
    }

    @Override
    public FunctionType getType() {
        List<Type> paramTypes = getParams().stream().map(Symbol::getType).collect(Collectors.toList());
        Type returnType = getReturnType();
        return new FunctionType(paramTypes, returnType);
    }


    @Override
    public ZenSymbolKind getKind() {
        if (isConstructor) {
            return ZenSymbolKind.CONSTRUCTOR;
        }
        if (getOperatorType() != OperatorType.NOT_OPERATOR) {
            return ZenSymbolKind.OPERATOR;
        }
        return ZenSymbolKind.FUNCTION;
    }

    @Override
    public List<Symbol> getMembers() {
        return Collections.emptyList();
    }

    public List<VariableSymbol> getParams() {
        return new ParamsResolver(getUnit()).resolve(getOwner());
    }

    public Type getReturnType() {
        return new ReturnTypeResolver(getUnit()).resolve(getOwner());
    }

    public boolean isConstructor() {
        return isConstructor;
    }


}
