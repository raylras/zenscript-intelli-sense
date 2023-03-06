package raylras.zen.code.tree;

import raylras.zen.code.tree.expr.*;
import raylras.zen.code.tree.stmt.*;

import java.util.stream.Collectors;

public class Pretty implements TreeVisitor<String> {

    @Override
    public String visitCompilationUnit(CompilationUnit node) {
        return "...";
    }

    @Override
    public String visitImportDecl(ImportDecl node) {
        StringBuilder sb = new StringBuilder();
        sb.append(node.fullName.stream().map(name -> name.literal).collect(Collectors.joining(".")));
        if (node.alias != null)
            sb.append(" as ").append(node.alias.literal);
        return sb.toString();
    }

    @Override
    public String visitClassDecl(ClassDecl node) {
        return "zenClass " + node.name.literal + " { ... }";
    }

    @Override
    public String visitConstructorDecl(ConstructorDecl node) {
        return "zenConstructor " + " { ... }";
    }

    @Override
    public String visitFunctionDecl(FunctionDecl node) {
        StringBuilder sb = new StringBuilder();
        sb.append("function ").append(node.name.literal);
        sb.append(node.params.stream().map(param -> param.accept(this)).collect(Collectors.joining(", ", "(", ")")));
        if (node.returnType != null)
            sb.append(" as ").append(node.returnType.accept(this));
        sb.append(" { ... }");
        return sb.toString();
    }

    @Override
    public String visitParameterDecl(ParameterDecl node) {
        StringBuilder sb = new StringBuilder();
        sb.append(node.name.literal);
        if (node.typeDecl != null)
            sb.append(" as ").append(node.typeDecl.accept(this));
        if (node.defaultValue != null)
            sb.append(" = ").append(node.defaultValue.accept(this));
        return sb.toString();
    }

    @Override
    public String visitName(Name node) {
        return node.literal;
    }

    @Override
    public String visitTypeLiteral(TypeLiteral node) {
        return node.literal;
    }

    @Override
    public String visitExpression(Expression node) {
        return node.accept(this);
    }

    @Override
    public String visitArrayAccess(ArrayAccess node) {
        return node.left.accept(this) + "[" + node.index.accept(this) + "]";
    }

    @Override
    public String visitArrayLiteral(ArrayLiteral node) {
        return "[ ... ]";
    }

    @Override
    public String visitAssignment(Assignment node) {
        return node.left.accept(this) + " " + node.op.literal + " " + node.right.accept(this);
    }

    @Override
    public String visitBinary(Binary node) {
        return node.left.accept(this) + " " + node.op.literal + " " + node.right.accept(this);
    }

    @Override
    public String visitBracketHandler(BracketHandler node) {
        return node.literal;
    }

    @Override
    public String visitCall(Call node) {
        return node.left.accept(this) + node.args.stream().map(arg -> arg.accept(this)).collect(Collectors.joining(", ", "(", ")"));
    }

    @Override
    public String visitConstantExpr(ConstantExpr node) {
        return String.valueOf(node.value);
    }

    @Override
    public String visitFunctionExpr(FunctionExpr node) {
        StringBuilder sb = new StringBuilder();
        sb.append("function");
        sb.append(node.params.stream().map(param -> param.accept(this)).collect(Collectors.joining(", ", "(", ")")));
        if (node.typeDecl != null)
            sb.append(" as ").append(node.typeDecl.accept(this));
        sb.append(" { ... }");
        return sb.toString();
    }

    @Override
    public String visitIDExpr(IDExpr node) {
        return node.name.literal;
    }

    @Override
    public String visitIntRange(IntRange node) {
        return node.from.accept(this) + " .. " + node.to.accept(this);
    }

    @Override
    public String visitMapLiteral(MapLiteral node) {
        return "{ ... }";
    }

    @Override
    public String visitMapEntry(MapEntry node) {
        return node.key.accept(this) + " : " + node.value.accept(this);
    }

    @Override
    public String visitMemberAccess(MemberAccess node) {
        return node.left.accept(this) + "." + node.right.accept(this);
    }

    @Override
    public String visitParens(Parens node) {
        return "(" + node.expr.accept(this) +")";
    }

    @Override
    public String visitTernary(Ternary node) {
        return node.condition.accept(this) + " ? " + node.truePart.accept(this) + " : " + node.falsePart.accept(this);
    }

    @Override
    public String visitThis(This node) {
        return "this";
    }

    @Override
    public String visitSuper(Super node) {
        return "super";
    }

    @Override
    public String visitTypeCast(TypeCast node) {
        return node.expr.accept(this) + " as " + node.type.accept(this);
    }

    @Override
    public String visitUnary(Unary node) {
        return node.op.literal + node.expr.accept(this);
    }

    @Override
    public String visitStatement(Statement node) {
        return node.accept(this);
    }

    @Override
    public String visitBlock(Block node) {
        return "{ ... }";
    }

    @Override
    public String visitBreak(Break node) {
        return "break";
    }

    @Override
    public String visitContinue(Continue node) {
        return "continue";
    }

    @Override
    public String visitExpressionStmt(ExpressionStmt node) {
        return node.expr.accept(this);
    }

    @Override
    public String visitForeach(Foreach node) {
        StringBuilder sb = new StringBuilder();
        sb.append("for ");
        sb.append(node.variables.stream().map(v -> v.accept(this)).collect(Collectors.joining(",")));
        sb.append(" in ").append(node.expression.accept(this));
        sb.append(" { ... }");
        return sb.toString();
    }

    @Override
    public String visitIf(If node) {
        StringBuilder sb = new StringBuilder();
        sb.append("if ").append(node.condition.accept(this));
        sb.append(" ").append(node.thenPart.accept(this));
        if (node.elsePart != null)
            sb.append(" else ").append(node.elsePart.accept(this));
        return sb.toString();
    }

    @Override
    public String visitReturn(Return node) {
        StringBuilder sb = new StringBuilder();
        sb.append("return");
        if (node.expr != null)
            sb.append(" ").append(node.expr.accept(this));
        return sb.toString();
    }

    @Override
    public String visitVariableDecl(VariableDecl node) {
        StringBuilder sb = new StringBuilder();
        switch (node.declarator) {
            case VAR:
                sb.append("var ");
                break;
            case VAL:
                sb.append("val ");
                break;
            case GLOBAL:
                sb.append("global ");
                break;
            case STATIC:
                sb.append("static ");
                break;
            case NONE:
                break;
            case INVALID:
            default:
                sb.append("/*invalid*/");
        }
        sb.append(node.name.accept(this));
        if (node.typeDecl != null)
            sb.append(" as ").append(node.typeDecl.accept(this));
        if (node.init != null)
            sb.append(" = ").append(node.init.accept(this));
        return sb.toString();
    }

    @Override
    public String visitWhile(While node) {
        return "while " + "(" + node.condition.accept(this) + ")" +  "{ ... }";
    }

    @Override
    public String visitTreeNode(TreeNode node) {
        if (node == null)
            return "/*missing*/";
        return "/*unknown/";
    }

}
