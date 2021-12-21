package raylras.zen.lsp;

import stanhebben.zenscript.ZenParsedFile;

// TODO
public abstract class ZenParsedFileVisitor<T> {

    private final ZenParsedFile parsedFile;

    public ZenParsedFileVisitor(ZenParsedFile parsedFile) {
        this.parsedFile = parsedFile;
    }

    abstract public T visitStatementBlock();

    abstract public T visitStatementBreak();

    abstract public T visitStatementContinue();

    abstract public T visitStatementExpression();

    abstract public T visitStatementForeach();

    abstract public T visitStatementIf();

    abstract public T visitStatementNull();

    abstract public T visitStatementReturn();

    abstract public T visitStatementWhileDo();

    abstract public T visitExpression();

    abstract public T visitExpressionAndAnd();

    abstract public T visitExpressionArgument();

    abstract public T visitExpressionArithmeticBinary();

    abstract public T visitExpressionArithmeticCompare();

    abstract public T visitExpressionArithmeticUnary();

    abstract public T visitExpressionArray();

    abstract public T visitExpressionArrayAdd();

    abstract public T visitExpressionArrayContains();

    abstract public T visitExpressionArrayGet();

    abstract public T visitExpressionArrayLength();

    abstract public T visitExpressionArrayList();

    abstract public T visitExpressionArrayListAdd();

    abstract public T visitExpressionArrayListGet();

    abstract public T visitExpressionArrayListRemove();

    abstract public T visitExpressionArrayListSet();

    abstract public T visitExpressionArraySet();

    abstract public T visitExpressionAs();

    abstract public T visitExpressionBool();

    abstract public T visitExpressionCallStatic();

    abstract public T visitExpressionCallVirtual();

    abstract public T visitExpressionCompareGeneric();

    abstract public T visitExpressionConditional();

    abstract public T visitExpressionEntryGet();

    abstract public T visitExpressionFloat();

    abstract public T visitExpressionFunction();

    abstract public T visitExpressionFunctionCall();

    abstract public T visitExpressionGlobalGet();

    abstract public T visitExpressionGlobalSet();

    abstract public T visitExpressionInstanceOf();

    abstract public T visitExpressionInt();

    abstract public T visitExpressionIntegerRange();

    abstract public T visitExpressionInvalid();

    abstract public T visitExpressionJavaLambda();

    abstract public T visitExpressionJavaLambdaSimpleGeneric();

    abstract public T visitExpressionJavaMethodCurled();

    abstract public T visitExpressionJavaMethodStatic();

    abstract public T visitExpressionLocalGet();

    abstract public T visitExpressionLocalSet();

    abstract public T visitExpressionMap();

    abstract public T visitExpressionMapContains();

    abstract public T visitExpressionMapEntrySet();

    abstract public T visitExpressionMapIndexGet();

    abstract public T visitExpressionMapIndexSet();

    abstract public T visitExpressionMapSize();

    abstract public T visitExpressionNothing();

    abstract public T visitExpressionNull();

    abstract public T visitExpressionOrOr();

    abstract public T visitExpressionString();

    abstract public T visitExpressionStringConcat();

    abstract public T visitExpressionStringContains();

    abstract public T visitExpressionStringIndex();

    abstract public T visitExpressionStringMethod();

    abstract public T visitExpressionThis();

}
