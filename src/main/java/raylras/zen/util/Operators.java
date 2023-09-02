package raylras.zen.util;

import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.symbol.Operator;
import raylras.zen.code.symbol.OperatorFunctionSymbol;
import raylras.zen.code.symbol.ParameterSymbol;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.IntersectionType;
import raylras.zen.code.type.SubtypeResult;
import raylras.zen.code.type.Type;

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

    public static Type getBinaryOperatorResult(Type type, Operator operator, CompilationEnvironment env, Type rightType) {
        return find(type, env, operator).stream()
                .min(Comparator.comparing(it -> rightType.isSubtypeOf(it.getParameterList().get(0).getType(), env), SubtypeResult.PRIORITY_COMPARATOR))
                .map(OperatorFunctionSymbol::getReturnType)
                .orElse(AnyType.INSTANCE);
    }

    public static Type getUnaryOperatorResult(Type type, Operator operator, CompilationEnvironment env) {
        return find(type, env, operator).stream()
                .findFirst()
                .map(OperatorFunctionSymbol::getReturnType)
                .orElse(AnyType.INSTANCE);
    }

    public static Optional<OperatorFunctionSymbol> findBestBinaryOperator(List<Symbol> symbols, Type rightType, CompilationEnvironment env) {
        return symbols.stream()
                .filter(it -> it instanceof OperatorFunctionSymbol)
                .map(it -> (OperatorFunctionSymbol) it)
                .min(Comparator.comparing(it -> rightType.isSubtypeOf(it.getParameterList().get(0).getType(), env), SubtypeResult.PRIORITY_COMPARATOR));
    }

    public static Type getTrinaryOperatorResult(Type type, Operator operator, CompilationEnvironment env, Type rightType1, Type rightType2) {
        return find(type, env, operator).stream()
                .min(Comparator.comparing(it -> {
                    List<ParameterSymbol> parameterList = it.getParameterList();
                    return SubtypeResult.higher(rightType1.isSubtypeOf(parameterList.get(0).getType(), env), rightType2.isSubtypeOf(parameterList.get(1).getType(), env));
                }, SubtypeResult.PRIORITY_COMPARATOR))
                .map(OperatorFunctionSymbol::getReturnType)
                .orElse(AnyType.INSTANCE);
    }

    public static boolean hasCaster(Type type, Type target, CompilationEnvironment env) {
        Type result = getUnaryOperatorResult(type, Operator.AS, env);
        if (result instanceof IntersectionType intersectionType) {
            return intersectionType.getTypeList().contains(target);
        } else {
            return target.equals(type);
        }
    }

    public static Operator of(String literal, int params) {
        Operator.OperatorType operatorType = switch (params) {
            case 0 -> Operator.OperatorType.UNARY;
            case 1 -> Operator.OperatorType.BINARY;
            case 2 -> Operator.OperatorType.TRINARY;
            default -> throw new IllegalArgumentException("No such operator for " + params + " parameters");
        };
        return operatorType.getOperators().getOrDefault(literal, Operator.ERROR);
    }

    public static Operator of(String literal, Operator.OperatorType type) {
        return type.getOperators().getOrDefault(literal, Operator.ERROR);
    }

}
