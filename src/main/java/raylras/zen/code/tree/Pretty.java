package raylras.zen.code.tree;

import raylras.zen.code.tree.expr.*;
import raylras.zen.code.tree.stmt.*;

import java.util.Objects;
import java.util.stream.Collectors;

public class Pretty extends TreeVisitor {

    private final StringBuilder result;

    public Pretty(TreeNode node) {
        result = new StringBuilder();
        node.accept(this);
    }

    @Override
    public String toString() {
        return result.toString();
    }

    @Override
    public boolean visit(CompilationUnit node) {
        result.append("...");
        return false;
    }

    @Override
    public boolean visit(ImportDecl node) {
        result.append(node.fullName.stream().map(Object::toString).collect(Collectors.joining(".")));
        if (node.alias != null)
            result.append(" as ").append(node.alias);
        return false;
    }

    @Override
    public boolean visit(ClassDecl node) {
        result.append("zenClass ");
        result.append(node.name);
        result.append(" { ... }");
        return false;
    }

    @Override
    public boolean visit(ConstructorDecl node) {
        result.append("zenConstructor { ... }");
        return false;
    }

    @Override
    public boolean visit(FunctionDecl node) {
        result.append("function ").append(node.name);
        result.append("(");
        result.append(node.params.stream().map(Objects::toString).collect(Collectors.joining(", ")));
        result.append(")");
        if (node.returnType != null)
            result.append(" as ").append(node.returnType);
        result.append(" { ... }");
        return false;
    }

    @Override
    public boolean visit(ParameterDecl node) {
        result.append(node.name);
        if (node.typeDecl != null)
            result.append(" as ").append(node.typeDecl);
        if (node.defaultValue != null)
            result.append(" = ").append(node.defaultValue);
        return false;
    }

    @Override
    public boolean visit(Name node) {
        result.append(node.literal);
        return false;
    }

    @Override
    public boolean visit(TypeLiteral node) {
        result.append(node.literal);
        return false;
    }

    @Override
    public boolean visit(ArrayAccess node) {
        result.append(node.left).append("[").append(node.index).append("]");
        return false;
    }

    @Override
    public boolean visit(ArrayLiteral node) {
        result.append("[ ... ]");
        return false;
    }

    @Override
    public boolean visit(Assignment node) {
        result.append(node.left);
        result.append(" ").append(node.op).append(" ");
        result.append(node.right);
        return false;
    }

    @Override
    public boolean visit(Binary node) {
        result.append(node.left);
        result.append(" ").append(node.op).append(" ");
        result.append(node.right);
        return false;
    }

    @Override
    public boolean visit(BracketHandler node) {
        result.append(node.literal);
        return false;
    }

    @Override
    public boolean visit(Call node) {
        result.append(node.left);
        result.append("(");
        result.append(node.args.stream().map(Object::toString).collect(Collectors.joining(", ")));
        result.append(")");
        return false;
    }

    @Override
    public boolean visit(ConstantExpr node) {
        result.append(node.value);
        return false;
    }

    @Override
    public boolean visit(FunctionExpr node) {
        result.append("function");
        result.append("(");
        result.append(node.params.stream().map(Object::toString).collect(Collectors.joining(", ")));
        result.append(")");
        if (node.typeDecl != null)
            result.append(" as ").append(node.typeDecl);
        result.append(" { ... }");
        return false;
    }

    @Override
    public boolean visit(IDExpr node) {
        result.append(node.name);
        return false;
    }

    @Override
    public boolean visit(IntRange node) {
        result.append(node.from).append(" .. ").append(node.to);
        return false;
    }

    @Override
    public boolean visit(MapLiteral node) {
        result.append("{ ... }");
        return false;
    }

    @Override
    public boolean visit(MapEntry node) {
        result.append(node.key).append(" : ").append(node.value);
        return false;
    }

    @Override
    public boolean visit(MemberAccess node) {
        result.append(node.left).append(".").append(node.right);
        return false;
    }

    @Override
    public boolean visit(Parens node) {
        result.append("(").append(node.expr).append(")");
        return false;
    }

    @Override
    public boolean visit(Ternary node) {
        result.append(node.condition);
        result.append("?").append(node.truePart);
        result.append(":").append(node.falsePart);
        return false;
    }

    @Override
    public boolean visit(This node) {
        result.append("this");
        return false;
    }

    @Override
    public boolean visit(Super node) {
        result.append("super");
        return false;
    }

    @Override
    public boolean visit(TypeCast node) {
        result.append(node.expr).append(" as ").append(node.type);
        return false;
    }

    @Override
    public boolean visit(Unary node) {
        result.append(node.op).append(node.expr);
        return false;
    }

    @Override
    public boolean visit(Block node) {
        result.append("{ ... }");
        return false;
    }

    @Override
    public boolean visit(Break node) {
        result.append("break");
        return false;
    }

    @Override
    public boolean visit(Continue node) {
        result.append("continue");
        return false;
    }

    @Override
    public boolean visitExpressionStmt(ExpressionStmt node) {
        result.append(node.expr).append(";");
        return false;
    }

    @Override
    public boolean visit(Foreach node) {
        result.append("for ");
        result.append(node.variables.stream().map(Object::toString).collect(Collectors.joining(", ")));
        result.append(" in ").append(node.expression);
        result.append(" { ... }");
        return false;
    }

    @Override
    public boolean visit(If node) {
        result.append("if ").append(node.condition);
        result.append(" ").append(node.thenPart);
        if (node.elsePart != null)
            result.append(" else ").append(node.elsePart);
        return false;
    }

    @Override
    public boolean visit(Return node) {
        result.append("return");
        if (node.expr != null)
            result.append(" ").append(node.expr);
        return false;
    }

    @Override
    public boolean visit(VariableDecl node) {
        result.append(node.declarator);
        if (node.declarator != Declarator.NONE)
            result.append(" ");
        result.append(node.name);
        if (node.typeDecl != null)
            result.append(" as ").append(node.typeDecl);
        if (node.init != null)
            result.append(" = ").append(node.init);
        return false;
    }

    @Override
    public boolean visit(While node) {
        result.append("while (").append(node.condition).append(")");
        result.append(" { ... }");
        return false;
    }

    public boolean visit(TreeNode node) {
        if (node == null)
            result.append("MISSING");
        result.append("UNKNOWN");
        return false;
    }

}
