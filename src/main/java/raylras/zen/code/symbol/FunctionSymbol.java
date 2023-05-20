package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.resolve.ParamsResolver;
import raylras.zen.code.resolve.ReturnTypeResolver;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;

import java.lang.invoke.MethodType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionSymbol extends Symbol {

    private final boolean isConstructor;


    public FunctionSymbol(ParseTree owner, CompilationUnit unit, boolean isConstructor) {
        super(owner, unit);
        this.isConstructor = isConstructor;
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
