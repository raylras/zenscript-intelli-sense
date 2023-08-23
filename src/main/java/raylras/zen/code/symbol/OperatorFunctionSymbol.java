package raylras.zen.code.symbol;

import raylras.zen.code.type.Type;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface OperatorFunctionSymbol extends Symbol {

    Operator getOperator();

    List<ParameterSymbol> getParameterList();

    Type getReturnType();

    enum Operator {
        ADD("+"),
        SUB("-"),
        MUL("*"),
        DIV("/"),
        MOD("%"),
        CAT("~"),
        OR("|"),
        AND("&"),
        XOR("^"),
        NEG("-"),
        NOT("!"),
        INDEX_GET("[]"),
        INDEX_SET("[]="),
        INT_RANGE(".."),
        HAS("has"),
        COMPARE("compare"),
        MEMBER_GET("."),
        MEMBER_SET(".="),
        EQUALS("=="),
        AS("as"),
        ITERATOR("iterator"),
        ERROR("");

        private final String literal;
        private static final Map<String, Operator> OPERATOR_MAP = Arrays.stream(Operator.values())
                .collect(Collectors.toMap(Operator::getLiteral, Function.identity()));

        Operator(String literal) {
            this.literal = literal;
        }

        public String getLiteral() {
            return literal;
        }

        public static Operator ofLiteral(String literal) {
            Operator operator = OPERATOR_MAP.get(literal);
            return (operator != null) ? operator : Operator.ERROR;
        }
    }

}
