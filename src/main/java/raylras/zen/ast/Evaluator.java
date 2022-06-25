package raylras.zen.ast;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.ast.expr.BinaryExpression;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.expr.IntLiteral;
import raylras.zen.ast.expr.Operator;
import raylras.zen.ast.visit.DefaultVisitor;
import raylras.zen.ast.visit.ExpressionVisitor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiFunction;

public class Evaluator extends DefaultVisitor<BigDecimal> {

    public static void main(String[] args) {
        System.out.println(eval("1 + 2"));
    }

    private static Map<Operator.Binary, BiFunction<BigDecimal,BigDecimal,BigDecimal>> operations = Map.of(
            Operator.Binary.ADD, BigDecimal::add,
            Operator.Binary.SUB, BigDecimal::subtract,
            Operator.Binary.MUL, BigDecimal::multiply,
            Operator.Binary.DIV, BigDecimal::divide
    );

    public static BigDecimal eval(String source) {
        Expression expr = parse(source).accept(new ExpressionVisitor(new ASTBuilder()));
        return expr.accept(new Evaluator());
    }

    @Override
    public BigDecimal visit(BinaryExpression binaryExpr) {
        return operations.get(binaryExpr.getOperator()).apply(
                binaryExpr.getLeft().accept(this),
                binaryExpr.getRight().accept(this)
        );
    }

    @Override
    public BigDecimal visit(IntLiteral intExpr) {
        return new BigDecimal(intExpr.getValue());
    }

    private static ZenScriptParser.ExpressionContext parse(String source) {
        CharStream charStream = CharStreams.fromString(source);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);
        return parser.expression();
    }

}


