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
import java.util.stream.Stream;

public class Operators {

    /**
     * Finds all operator functions of the type {@code t} with the operator {@code op}.
     *
     * @param t   the type to search
     * @param op  the operator to search
     * @param env the compilation environment
     * @return the operator functions
     */
    public static Stream<OperatorFunctionSymbol> find(Type t, Operator op, CompilationEnvironment env) {
        return Symbols.getMembers(t, OperatorFunctionSymbol.class, env)
                .filter(it -> it.getOperator() == op);
    }

    /**
     * Applies the unary operator {@code op} to the type {@code t}.
     *
     * @param t   the type to which the operator will be applied
     * @param op  the unary operator to apply
     * @param env the compilation environment
     * @return the resulting type after applying the operator
     */
    public static Optional<Type> apply(Type t, Operator op, CompilationEnvironment env) {
        return find(t, op, env)
                .findFirst()
                .map(OperatorFunctionSymbol::getReturnType);
    }

    /**
     * Applies the binary operator {@code op} to the types {@code t1} and {@code t2}.
     *
     * @param t1  the first type
     * @param t2  the second type
     * @param op  the binary operator to apply
     * @param env the compilation environment
     * @return the resulting type after applying the binary operator
     */
    public static Optional<Type> apply(Type t1, Type t2, Operator op, CompilationEnvironment env) {
        return find(t1, op, env)
                .max(Comparator.comparing(it -> Types.test(t2, it.getParameterList().get(0).getType(), env), SubtypeResult.PRIORITY_COMPARATOR))
                .map(OperatorFunctionSymbol::getReturnType);
    }

    /**
     * Applies the ternary operator {@code op} to the types {@code t1}, {@code t2}, and {@code t3}.
     *
     * @param t1  the first type
     * @param t2  the second type
     * @param t3  the third type
     * @param op  the ternary operator to apply
     * @param env the compilation environment
     * @return the resulting type after applying the ternary operator
     */
    public static Optional<Type> apply(Type t1, Type t2, Type t3, Operator op, CompilationEnvironment env) {
        return find(t1, op, env)
                .max(Comparator.comparing(it -> {
                    List<ParameterSymbol> parameterList = it.getParameterList();
                    return SubtypeResult.lower(Types.test(t2, parameterList.get(0).getType(), env), Types.test(t3, parameterList.get(1).getType(), env));
                }, SubtypeResult.PRIORITY_COMPARATOR))
                .map(OperatorFunctionSymbol::getReturnType);
    }

    public static Optional<OperatorFunctionSymbol> findBestBinaryOperator(List<Symbol> symbols, Type rightType, CompilationEnvironment env) {
        return symbols.stream()
                .filter(it -> it instanceof OperatorFunctionSymbol)
                .map(it -> (OperatorFunctionSymbol) it)
                .max(Comparator.comparing(it -> Types.test(rightType, it.getParameterList().get(0).getType(), env), SubtypeResult.PRIORITY_COMPARATOR));
    }

    public static boolean hasCaster(Type source, Type target, CompilationEnvironment env) {
        return apply(source, Operator.AS, env)
                .filter(resultType -> resultType.isSuperclassTo(target))
                .isPresent();
    }

}
