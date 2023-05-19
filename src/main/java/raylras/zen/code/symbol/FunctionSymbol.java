package raylras.zen.code.symbol;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.NameResolver;
import raylras.zen.code.resolve.ParamsResolver;
import raylras.zen.code.resolve.ReturnTypeResolver;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionSymbol extends Symbol {

    private final boolean isConstructor;

    public FunctionSymbol(ParseTree owner, CompilationUnit unit) {
        super(owner, unit);
        isConstructor = false;
    }


    public FunctionSymbol(ParseTree owner, CompilationUnit unit, boolean isConstructor) {
        super(owner, unit);
        this.isConstructor = isConstructor;
    }


    @Override
    public String getName() {
        return new NameResolver().resolve(owner);
    }

    @Override
    public Type getType() {
        List<Type> paramTypes = getParams().stream().map(Symbol::getType).collect(Collectors.toList());
        Type returnType = getReturnType();
        return new FunctionType(paramTypes, returnType);
    }

    @Override
    public Kind getKind() {
        return Kind.FUNCTION;
    }

    @Override
    public List<Symbol> getMembers() {
        return Collections.emptyList();
    }

    public List<VariableSymbol> getParams() {
        return new ParamsResolver(unit).resolve(owner);
    }

    public Type getReturnType() {
        return new ReturnTypeResolver(unit).resolve(owner);
    }

    public boolean isConstructor() {
        return isConstructor;
    }


}
