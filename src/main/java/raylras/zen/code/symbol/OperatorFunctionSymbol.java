package raylras.zen.code.symbol;

import raylras.zen.code.CompilationUnit;
import raylras.zen.code.parser.ZenScriptParser.OperatorFunctionDeclarationContext;
import raylras.zen.code.resolve.FormalParameterResolver;
import raylras.zen.code.resolve.TypeResolver;
import raylras.zen.code.type.FunctionType;
import raylras.zen.code.type.Type;

import java.util.List;
import java.util.stream.Collectors;

public class OperatorFunctionSymbol extends Symbol {

    public OperatorFunctionSymbol(String name, OperatorFunctionDeclarationContext cst, CompilationUnit unit) {
        super(name, cst, unit);
    }

    public List<ParameterSymbol> getParameterList() {
        return FormalParameterResolver.getFormalParameterList(cst, unit);
    }

    @Override
    public FunctionType getType() {
        Type type = TypeResolver.getType(cst, unit);
        if (type instanceof FunctionType) {
            return (FunctionType) type;
        } else {
            return null;
        }
    }

    @Override
    public Kind getKind() {
        return Kind.FUNCTION;
    }

    @Override
    public OperatorFunctionDeclarationContext getCst() {
        return (OperatorFunctionDeclarationContext) cst;
    }

    @Override
    public String getNameWithType() {
        FunctionType type = getType();
        return getParameterList().stream()
                .map(ParameterSymbol::getNameWithType)
                .collect(Collectors.joining(", ", name + "(", ") as " + type.getReturnType()));
    }

}
