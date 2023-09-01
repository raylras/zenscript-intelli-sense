package raylras.zen.util;

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
    public static List<OperatorFunctionSymbol> find(Type type, Operator operator) {
        return Symbols.getMember(type, OperatorFunctionSymbol.class, it -> it.getOperator() == operator);
    }

    public static List<OperatorFunctionSymbol> find(Type type, Operator... operators) {
        Set<Operator> candidates = Set.of(operators);
        return Symbols.getMember(type, OperatorFunctionSymbol.class, it -> candidates.contains(it.getOperator()));
    }

    public static Type getBinaryOperatorResult(Type type, Operator operator, Type rightType) {
        return find(type, operator).stream()
                .min(Comparator.comparing(it -> rightType.isSubtypeOf(it.getParameterList().get(0).getType()), SubtypeResult.PRIORITY_COMPARATOR))
                .map(OperatorFunctionSymbol::getReturnType)
                .orElse(AnyType.INSTANCE);
    }

    public static Type getUnaryOperatorResult(Type type, Operator operator) {
        return find(type, operator).stream()
                .findFirst()
                .map(OperatorFunctionSymbol::getReturnType)
                .orElse(AnyType.INSTANCE);
    }

    public static Optional<OperatorFunctionSymbol> findBestBinaryOperator(List<Symbol> symbols, Type rightType) {
        return symbols.stream()
                .filter(it -> it instanceof OperatorFunctionSymbol)
                .map(it -> (OperatorFunctionSymbol) it)
                .min(Comparator.comparing(it -> rightType.isSubtypeOf(it.getParameterList().get(0).getType()), SubtypeResult.PRIORITY_COMPARATOR));
    }

    public static Type getTrinaryOperatorResult(Type type, Operator operator, Type rightType1, Type rightType2) {
        return find(type, operator).stream()
                .min(Comparator.comparing(it -> {
                    List<ParameterSymbol> parameterList = it.getParameterList();
                    return SubtypeResult.higher(rightType1.isSubtypeOf(parameterList.get(0).getType()), rightType2.isSubtypeOf(parameterList.get(1).getType()));
                }, SubtypeResult.PRIORITY_COMPARATOR))
                .map(OperatorFunctionSymbol::getReturnType)
                .orElse(AnyType.INSTANCE);
    }

    public static boolean hasCaster(Type type, Type target) {
        Type result = getUnaryOperatorResult(type, Operator.AS);
        if (result instanceof IntersectionType intersectionType) {
            return intersectionType.getTypeList().contains(target);
        } else {
            return target.equals(type);
        }
    }

    public static Operator literal(String literal, int params) {
        Operator.OperatorType operatorType = switch (params) {
            case 1 -> Operator.OperatorType.UNARY;
            case 2 -> Operator.OperatorType.BINARY;
            case 3 -> Operator.OperatorType.TRINARY;
            default -> throw new IllegalArgumentException("No such operator for " + params + " parameters");
        };
        return operatorType.getOperators().getOrDefault(literal, Operator.ERROR);
    }
}
