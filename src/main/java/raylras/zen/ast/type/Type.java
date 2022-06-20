package raylras.zen.ast.type;

import raylras.zen.ast.expr.Operator;

import java.util.List;
import java.util.stream.Collectors;

public interface Type {

    default Type not() {
        return new ErrorType(String.format("cannot reverse(%s) %s", Operator.Unary.LOGIC_NOT, this));
    }

    default Type neg() {
        return new ErrorType(String.format("cannot negate(%s) %s", Operator.Unary.NEG, this));
    }

    default Type add(Type that) {
        return new ErrorType(String.format("cannot add(%s) %s with %s", Operator.Binary.ADD, this , that));
    }

    default Type sub(Type that) {
        return new ErrorType(String.format("cannot subtract(%s) %s from %s", Operator.Binary.SUB, this, that));
    }

    default Type mul(Type that) {
        return new ErrorType(String.format("cannot multiply(%s) %s with %s", Operator.Binary.MUL, this, that));
    }

    default Type div(Type that) {
        return new ErrorType(String.format("cannot divide(%s) %s by %s", Operator.Binary.DIV, this, that));
    }

    default Type bitwiseAnd(Type that) {
        return new ErrorType(String.format("cannot compute(%s) %s and %s", Operator.Binary.BIT_AND, this, that));
    }

    default Type bitwiseOr(Type that) {
        return new ErrorType(String.format("cannot compute(%s) %s or %s", Operator.Binary.BIT_OR, this, that));
    }

    default Type xor(Type that) {
        return new ErrorType(String.format("cannot compute(%s) %s xor %s", Operator.Binary.BIT_XOR, this, that));
    }

    default Type and(Type that) {
        return new ErrorType(String.format("cannot compute(%s) %s and %s", Operator.Binary.LOGIC_AND, this, that));
    }

    default Type or(Type that) {
        return new ErrorType(String.format("cannot compute(%s) %s or %s", Operator.Binary.LOGIC_OR, this, that));
    }

    default Type compare(Type that) {
        return new ErrorType(String.format("cannot compare(%s) %s with %s", Operator.Binary.getComparisons().stream().map(Operator.Binary::toString).collect(Collectors.joining(", ")), this, that));
    }

    default Type cat(Type that) {
        return new ErrorType(String.format("cannot concatenate(%s) %s with %s", Operator.Binary.CAT, this, that));
    }

    default Type in(Type that) {
        return new ErrorType(String.format("cannot compute(%s) %s in %s", Operator.Binary.IN + ", has", this, that));
    }

    default Type index(Type that) {
        return new ErrorType(String.format("cannot index([]) %s with %s", this, that));
    }

    default Type call(List<Type> args) {
        return new ErrorType(String.format("cannot call %s using (%s)", this, args.stream().map(Object::toString).collect(Collectors.joining(", "))));
    }

    default Type assign(Type source) {
        return new ErrorType(String.format("cannot assign %s to %s", source, this));
    }

    default boolean equivalent(Type that) {
        return this.equals(that);
    }

}
