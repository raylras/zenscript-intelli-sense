package raylras.zen.util;

import raylras.zen.code.symbol.OperatorFunctionSymbol;
import raylras.zen.code.symbol.ParameterSymbol;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.SubtypeResult;
import raylras.zen.code.type.Type;
import raylras.zen.code.type.UnionType;

import java.util.Comparator;
import java.util.List;

/**
 * @author youyihj
 */
public class Operators {
    public static List<OperatorFunctionSymbol> find(Type type, OperatorFunctionSymbol.Operator operator) {
        return Symbols.getMembersByName(type, operator.getLiteral(), OperatorFunctionSymbol.class);
    }

    public static Type getBinaryOperatorResult(Type type, OperatorFunctionSymbol.Operator operator, Type rightType) {
        return find(type, operator).stream()
                .filter(it -> it.getParameterList().size() == 1)
                .min(Comparator.comparing(it -> rightType.isSubtypeOf(it.getParameterList().get(0).getType()), SubtypeResult.PRIORITY_COMPARATOR))
                .map(OperatorFunctionSymbol::getReturnType)
                .orElse(AnyType.INSTANCE);
    }

    public static Type getUnaryOperatorResult(Type type, OperatorFunctionSymbol.Operator operator) {
        return find(type, operator).stream()
                .filter(it -> it.getParameterList().isEmpty())
                .findFirst()
                .map(OperatorFunctionSymbol::getReturnType)
                .orElse(AnyType.INSTANCE);
    }

    public static Type getTrinaryOperatorResult(Type type, OperatorFunctionSymbol.Operator operator, Type rightType1, Type rightType2) {
        return find(type, operator).stream()
                .filter(it -> it.getParameterList().size() == 2)
                .min(Comparator.comparing(it -> {
                    List<ParameterSymbol> parameterList = it.getParameterList();
                    return SubtypeResult.higher(rightType1.isSubtypeOf(parameterList.get(0).getType()), rightType2.isSubtypeOf(parameterList.get(1).getType()));
                }, SubtypeResult.PRIORITY_COMPARATOR))
                .map(OperatorFunctionSymbol::getReturnType)
                .orElse(AnyType.INSTANCE);
    }

    public static boolean hasCaster(Type type, Type target) {
        Type result = getUnaryOperatorResult(type, OperatorFunctionSymbol.Operator.AS);
        if (result instanceof UnionType unionType) {
            return unionType.getTypeList().contains(target);
        } else {
            return target.equals(type);
        }
    }
}
