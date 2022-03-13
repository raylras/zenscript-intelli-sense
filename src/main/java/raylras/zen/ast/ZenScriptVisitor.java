package raylras.zen.ast;

import org.antlr.v4.runtime.tree.TerminalNode;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.antlr.ZenScriptParserBaseVisitor;
import raylras.zen.ast.expr.Expression;
import raylras.zen.ast.expr.ExpressionBinary;
import raylras.zen.ast.expr.ExpressionVariable;
import raylras.zen.ast.expr.Operator;
import raylras.zen.ast.scope.Scope;
import raylras.zen.ast.stmt.Statement;
import raylras.zen.ast.stmt.StatementExpression;
import raylras.zen.ast.stmt.StatementReturn;
import raylras.zen.ast.stmt.StatementVar;
import raylras.zen.ast.type.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class ZenScriptVisitor extends ZenScriptParserBaseVisitor<ASTNode> {

    private final Environment env;
    private final Stack<Scope> scopeStack;
    private ScriptNode pScriptNode; // current script
    private List<ErrorNode> errorNodes;

    public ZenScriptVisitor(Environment env) {
        this.env = env;
        this.scopeStack = new Stack<>();
        this.errorNodes = new ArrayList<>();
    }

    public ScriptNode visitScript(ZenScriptParser.ScriptContext ctx, URI uri) {
        pScriptNode = new ScriptNode(uri).setSourcePosition(ctx);
        scopeStack.push(pScriptNode);

        ctx.importStatement().forEach(this::visitImportStatement);
        ctx.functionDeclaration().forEach(this::visitFunctionDeclaration);
        ctx.statement().forEach(this::visitStatement);

        popScope();
        return pScriptNode;
    }

    @Override
    public ImportNode visitImportStatement(ZenScriptParser.ImportStatementContext ctx) {
        ZenClassNode zenClassNode = new ZenClassNode(ctx.className().getText()).setSourcePosition(ctx.className());
        ImportNode importNode = new ImportNode(zenClassNode).setSourcePosition(ctx);

        TerminalNode alias = ctx.IDENTIFIER();
        if (alias != null) {
            importNode.setAlias(alias.getText()).setSourcePosition(alias.getSymbol());
        }

        pScriptNode.addImport(importNode);
        return importNode;
    }

    @Override
    public FunctionNode visitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        FunctionNode functionNode = new FunctionNode(ctx.IDENTIFIER().getText()).setSourcePosition(ctx);

        // function parameters
        if (ctx.parameters() != null) {
            List<VariableNode> parameters = ctx.parameters().parameter()
                    .stream()
                    .map(parameter -> {
                        TerminalNode name = parameter.IDENTIFIER();
                        VariableNode variableNode = new VariableNode(name.getText()).setSourcePosition(name.getSymbol());

                        // parameter type
                        if (parameter.asType() != null) {
                            variableNode.setType(visitAsType(ctx.asType()).getType());
                        }

                        return variableNode;
                    })
                    .collect(Collectors.toList());
            functionNode.setParameters(parameters);
        }

        // function return type
        if (ctx.asType() != null) {
            functionNode.setReturnType(visitAsType(ctx.asType()).getType());
        }

        // function statements
        List<Statement> statements = visitBlock(ctx.block()).getStatements();
        functionNode.setStatements(statements);

        pScriptNode.addFunction(functionNode);
        return functionNode;
    }

    @Override
    public BlockNode visitBlock(ZenScriptParser.BlockContext ctx) {
        BlockNode blockNode = new BlockNode();
        pushScope(blockNode);
        List<Statement> statements = ctx.statement()
                .stream()
                .map(this::visitStatement)
                .collect(Collectors.toList());
        blockNode.setStatements(statements);
        popScope();
        return blockNode;
    }

    @Override
    public Statement visitStatement(ZenScriptParser.StatementContext ctx) {
        Statement stmt = (Statement) visitChildren(ctx);
        scopeStack.peek().addStatement(stmt);
        return stmt;
    }

    @Override
    public StatementReturn visitReturnStatement(ZenScriptParser.ReturnStatementContext ctx) {
        StatementReturn statement = new StatementReturn().setSourcePosition(ctx);

        ZenScriptParser.ExpressionContext exprCtx = ctx.expression();
        if (exprCtx != null) {
            statement.setExpr((Expression) visit(exprCtx));
        }
        return statement;
    }

    @Override
    public ASTNode visitBreakStatement(ZenScriptParser.BreakStatementContext ctx) {
        return super.visitBreakStatement(ctx);
    }

    @Override
    public ASTNode visitContinueStatement(ZenScriptParser.ContinueStatementContext ctx) {
        return super.visitContinueStatement(ctx);
    }

    @Override
    public ASTNode visitIfStatement(ZenScriptParser.IfStatementContext ctx) {
        return super.visitIfStatement(ctx);
    }

    @Override
    public ASTNode visitForStatement(ZenScriptParser.ForStatementContext ctx) {
        return super.visitForStatement(ctx);
    }

    @Override
    public ASTNode visitWhileStatement(ZenScriptParser.WhileStatementContext ctx) {
        return super.visitWhileStatement(ctx);
    }

    @Override
    public StatementVar visitVarStatement(ZenScriptParser.VarStatementContext ctx) {
        StatementVar stmt = new StatementVar().setSourcePosition(ctx);

        String name = ctx.IDENTIFIER().getText();
        VariableNode variable = new VariableNode(name).setSourcePosition(ctx.IDENTIFIER().getSymbol());
        if (ctx.start.getType() == ZenScriptParser.VAL) { variable.setFinal(true); }

        stmt.setVariable(variable);
        scopeStack.peek().define(name,variable);

        return stmt;
    }

    @Override
    public StatementExpression visitExpressionStatement(ZenScriptParser.ExpressionStatementContext ctx) {
        StatementExpression stmt = new StatementExpression().setSourcePosition(ctx);
        Expression expr = (Expression) visit(ctx.expression());
        stmt.setExpr(expr);
        return stmt;
    }

    @Override
    public ExpressionBinary visitExpressionBinary(ZenScriptParser.ExpressionBinaryContext ctx) {
        ExpressionBinary binary = new ExpressionBinary().setSourcePosition(ctx);

        binary.setLeft((Expression) visit(ctx.getChild(0))); // left value
        binary.setRight((Expression) visit(ctx.getChild(2))); // right value
        binary.setOperator(Operator.get(ctx.getChild(1).getText())); // binary operator

        return binary;
    }

    @Override
    public ASTNode visitExpressionCall(ZenScriptParser.ExpressionCallContext ctx) {
        return super.visitExpressionCall(ctx);
    }

    @Override
    public Expression visitMemberCall(ZenScriptParser.MemberCallContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        VariableNode variableNode = (VariableNode) scopeStack.peek().resolve(name);

        if (variableNode == null) {
            variableNode = new VariableNode(name);
            ExpressionVariable expr = new ExpressionVariable().setSourcePosition(ctx);
            expr.setVariable(variableNode);
            pushError(new ErrorNode(expr, EnumError.COULD_NOT_FIND));
        }

        ExpressionVariable expr = new ExpressionVariable().setSourcePosition(ctx);
        expr.setVariable(variableNode);

        return expr;
    }

    @Override
    public TypeNode visitAsType(ZenScriptParser.AsTypeContext ctx) {
        return (TypeNode) visitChildren(ctx);
    }

    @Override
    public TypeNode visitTypePrimitive(ZenScriptParser.TypePrimitiveContext ctx) {
        TypeNode typeNode = new TypeNode();
        switch (ctx.getStart().getType()) {
            case ZenScriptParser.ANY: typeNode.setType(TypeAny.INSTANCE); break;
            case ZenScriptParser.BOOL: typeNode.setType(TypeBool.INSTANCE); break;
            case ZenScriptParser.BYTE: typeNode.setType(TypeByte.INSTANCE); break;
            case ZenScriptParser.SHORT: typeNode.setType(TypeShort.INSTANCE); break;
            case ZenScriptParser.INT: typeNode.setType(TypeInt.INSTANCE); break;
            case ZenScriptParser.LONG: typeNode.setType(TypeLong.INSTANCE); break;
            case ZenScriptParser.FLOAT: typeNode.setType(TypeFloat.INSTANCE); break;
            case ZenScriptParser.DOUBLE: typeNode.setType(TypeDouble.INSTANCE); break;
            case ZenScriptParser.STRING: typeNode.setType(TypeString.INSTANCE); break;
            case ZenScriptParser.VOID: typeNode.setType(TypeVoid.INSTANCE); break;
        }
        return typeNode;
    }

    // TODO: visit rest Type

    @Override
    public ASTNode visitTypeFunction(ZenScriptParser.TypeFunctionContext ctx) {
        return super.visitTypeFunction(ctx);
    }

    @Override
    public ASTNode visitTypeArray(ZenScriptParser.TypeArrayContext ctx) {
        return super.visitTypeArray(ctx);
    }

    @Override
    public ASTNode visitTypeList(ZenScriptParser.TypeListContext ctx) {
        return super.visitTypeList(ctx);
    }

    @Override
    public ASTNode visitTypeMap(ZenScriptParser.TypeMapContext ctx) {
        return super.visitTypeMap(ctx);
    }

    @Override
    public ASTNode visitTypeClass(ZenScriptParser.TypeClassContext ctx) {
        return super.visitTypeClass(ctx);
    }

    private void pushScope(Scope scope) {
        scope.setParent(scopeStack.peek());
        scopeStack.push(scope);
    }

    private void popScope() {
        scopeStack.pop();
    }

    private void pushError(ErrorNode errorNode) {
        errorNodes.add(errorNode);
    }

    // wrapper class, we just need to pass a type
    static class TypeNode extends ASTNode {
        private Type type;

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }
    }

}
