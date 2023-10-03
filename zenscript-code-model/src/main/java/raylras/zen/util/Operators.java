package raylras.zen.util;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.symbol.Operator;
import raylras.zen.model.symbol.OperatorFunctionSymbol;
import raylras.zen.model.symbol.ParameterSymbol;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.model.type.SubtypeResult;
import raylras.zen.model.type.Type;
import raylras.zen.model.type.Types;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Operators {

    public static List<OperatorFunctionSymbol> find(Type type, CompilationEnvironment env, Operator operator) {
        return Symbols.getMember(type, OperatorFunctionSymbol.class, env, it -> it.getOperator() == operator);
    }

    public static List<OperatorFunctionSymbol> find(Type type, CompilationEnvironment env, Operator... operators) {
        Set<Operator> candidates = Set.of(operators);
        return Symbols.getMember(type, OperatorFunctionSymbol.class, env, it -> candidates.contains(it.getOperator()));
    }

    public static Optional<Type> getUnaryResult(Type type, Operator operator, CompilationEnvironment env) {
        return find(type, env, operator).stream()
                .findFirst()
                .map(OperatorFunctionSymbol::getReturnType);
    }

    public static Optional<Type> getBinaryResult(Type type, Operator operator, CompilationEnvironment env, Type rightType) {
        return find(type, env, operator).stream()
                .min(Comparator.comparing(it -> Types.test(rightType, it.getParameterList().get(0).getType(), env), SubtypeResult.PRIORITY_COMPARATOR))
                .map(OperatorFunctionSymbol::getReturnType);
    }

    public static Optional<Type> getTrinaryResult(Type type, Operator operator, CompilationEnvironment env, Type rightType1, Type rightType2) {
        return find(type, env, operator).stream()
                .min(Comparator.comparing(it -> {
                    List<ParameterSymbol> parameterList = it.getParameterList();
                    return SubtypeResult.higher(Types.test(rightType1, parameterList.get(0).getType(), env), Types.test(rightType2, parameterList.get(1).getType(), env));
                }, SubtypeResult.PRIORITY_COMPARATOR))
                .map(OperatorFunctionSymbol::getReturnType);
    }

    public static Optional<OperatorFunctionSymbol> findBestBinaryOperator(List<Symbol> symbols, Type rightType, CompilationEnvironment env) {
        return symbols.stream()
                .filter(it -> it instanceof OperatorFunctionSymbol)
                .map(it -> (OperatorFunctionSymbol) it)
                .min(Comparator.comparing(it -> Types.test(rightType, it.getParameterList().get(0).getType(), env), SubtypeResult.PRIORITY_COMPARATOR));
    }

    public static boolean hasCaster(Type source, Type target, CompilationEnvironment env) {
        return getUnaryResult(source, Operator.AS, env)
                .map(type -> type.isSuperclassTo(target))
                .orElse(false);
    }

}
