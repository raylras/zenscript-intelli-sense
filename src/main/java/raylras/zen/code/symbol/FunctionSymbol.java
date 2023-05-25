package raylras.zen.code.symbol;

import com.google.common.collect.ImmutableSet;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.resolve.ParamsResolver;
import raylras.zen.code.resolve.ReturnTypeResolver;
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

    public Operator getOperatorType() {
        if (!isLibrarySymbol()) {
            return Operator.NOT_OPERATOR;
        }

        Map<String, String> annotations = getAnnotations();
        if (annotations.containsKey("caster")) {
            return Operator.CASTER;
        }
        return Operator.fromString(annotations.get("operator"));
    }

    public int getOptionalIndex() {
        if (!isLibrarySymbol()) {
            return -1;
        }

        List<VariableSymbol> params = resolveParams();
        for (int i = 0; i < params.size(); i++) {
            ZenScriptParser.ParameterContext parameterContext = (ZenScriptParser.ParameterContext) params.get(i).getOwner();
            if (parameterContext.defaultValue() != null) {
                return i;
            }
        }

        return -1;
    }

    public boolean isHidden() {
        return getAnnotations().containsKey("hidden");
    }

    @Override
    public String getName() {
        return NameResolver.resolveName(getOwner());
    }

    @Override
    public FunctionType getType() {
        List<Type> paramTypes = resolveParams()
            .stream()
            .map(Symbol::getType)
            .collect(Collectors.toList());
        Type returnType = getReturnType();
        return new FunctionType(paramTypes, returnType);
    }

    protected List<VariableSymbol> resolveParams() {
        return new ParamsResolver(getUnit()).resolve(getOwner());
    }

    public List<String> getParamNames() {
        return resolveParams()
            .stream()
            .map(Symbol::getName)
            .collect(Collectors.toList());
    }

    @Override
    public ZenSymbolKind getKind() {
        if (isConstructor) {
            return ZenSymbolKind.CONSTRUCTOR;
        }
        if (getOperatorType() != Operator.NOT_OPERATOR) {
            return ZenSymbolKind.OPERATOR;
        }
        return ZenSymbolKind.FUNCTION;
    }

    @Override
    public List<Symbol> getMembers() {
        return Collections.emptyList();
    }

    public Type getReturnType() {
        return new ReturnTypeResolver(getUnit()).resolve(getOwner());
    }

    public boolean isConstructor() {
        return isConstructor;
    }

}
