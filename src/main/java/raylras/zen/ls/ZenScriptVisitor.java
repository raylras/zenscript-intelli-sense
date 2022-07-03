package raylras.zen.ls;

import raylras.zen.ast.BlockNode;
import raylras.zen.ast.Node;
import raylras.zen.ast.ScriptNode;
import raylras.zen.ast.decl.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;
import raylras.zen.ast.visit.DefaultVisitor;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class ZenScriptVisitor extends DefaultVisitor<Object> {

    static class ASTLookupKey {
        private final Node node;

        public ASTLookupKey(Node node) {
            this.node = node;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ASTLookupKey that = (ASTLookupKey) o;
            return node.equals(that.node);
        }

        @Override
        public int hashCode() {
            return node.hashCode();
        }
    }

    static class ASTNodeLookupData {
        public Node parent;
        public URI uri;
    }

    private final Deque<Node> stack = new ArrayDeque<>();
    private final Map<ASTLookupKey, ASTNodeLookupData> lookup = new HashMap<>();
    private ScriptNode scriptNode;

    private void pushASTNode(Node node) {
        URI uri = scriptNode.getURI();

        ASTNodeLookupData data = new ASTNodeLookupData();
        data.uri = uri;
        if (stack.size() > 0) {
            data.parent = stack.peek();
        }
        lookup.put(new ASTLookupKey(node), data);

        stack.add(node);
    }

    private void popASTNode() {
        stack.pop();
    }

    @Override
    public Object visit(AliasDeclaration aliasDecl) {
        pushASTNode(aliasDecl);
        super.visit(aliasDecl);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(BlockNode blockNode) {
        pushASTNode(blockNode);
        super.visit(blockNode);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ConstructorDeclaration ctorDecl) {
        pushASTNode(ctorDecl);
        super.visit(ctorDecl);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(FunctionDeclaration funcDecl) {
        pushASTNode(funcDecl);
        super.visit(funcDecl);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ImportDeclaration importDecl) {
        pushASTNode(importDecl);
        super.visit(importDecl);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ParameterDeclaration paramDecl) {
        pushASTNode(paramDecl);
        super.visit(paramDecl);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ScriptNode scriptNode) {
        this.scriptNode = scriptNode;

        pushASTNode(scriptNode);
        super.visit(scriptNode);
        popASTNode();

        this.scriptNode = null;
        stack.clear();
        return null;
    }

    @Override
    public Object visit(TypeDeclaration typeDecl) {
        pushASTNode(typeDecl);
        super.visit(typeDecl);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ZenClassDeclaration classDecl) {
        pushASTNode(classDecl);
        super.visit(classDecl);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(BreakStatement breakStmt) {
        pushASTNode(breakStmt);
        super.visit(breakStmt);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ContinueStatement contStmt) {
        pushASTNode(contStmt);
        super.visit(contStmt);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ExpressionStatement exprStmt) {
        pushASTNode(exprStmt);
        super.visit(exprStmt);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ForeachStatement foreachStmt) {
        pushASTNode(foreachStmt);
        super.visit(foreachStmt);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(IfElseStatement ifStmt) {
        pushASTNode(ifStmt);
        super.visit(ifStmt);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ReturnStatement returnStmt) {
        pushASTNode(returnStmt);
        super.visit(returnStmt);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(VariableDeclStatement varDeclStmt) {
        pushASTNode(varDeclStmt);
        super.visit(varDeclStmt);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(WhileStatement whileStmt) {
        pushASTNode(whileStmt);
        super.visit(whileStmt);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ArgumentsExpression argsExpr) {
        pushASTNode(argsExpr);
        super.visit(argsExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ArrayLiteral arrayExpr) {
        pushASTNode(arrayExpr);
        super.visit(arrayExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(AssignmentExpression assignExpr) {
        pushASTNode(assignExpr);
        super.visit(assignExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(BinaryExpression binaryExpr) {
        pushASTNode(binaryExpr);
        super.visit(binaryExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(BoolLiteral boolExpr) {
        pushASTNode(boolExpr);
        super.visit(boolExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(BracketHandler bracketExpr) {
        pushASTNode(bracketExpr);
        super.visit(bracketExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(FunctionExpression funcExpr) {
        pushASTNode(funcExpr);
        super.visit(funcExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(VarAccessExpression varAccess) {
        pushASTNode(varAccess);
        super.visit(varAccess);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(MapEntryExpression entryExpr) {
        pushASTNode(entryExpr);
        super.visit(entryExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(MapLiteral mapExpr) {
        pushASTNode(mapExpr);
        super.visit(mapExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(MemberAccess memberAccess) {
        pushASTNode(memberAccess);
        super.visit(memberAccess);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(MemberIndexExpression memberIndex) {
        pushASTNode(memberIndex);
        super.visit(memberIndex);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(NullExpression nullExpr) {
        pushASTNode(nullExpr);
        super.visit(nullExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ParensExpression parensExpr) {
        pushASTNode(parensExpr);
        super.visit(parensExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(RangeExpression rangeExpr) {
        pushASTNode(rangeExpr);
        super.visit(rangeExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(StringLiteral stringExpr) {
        pushASTNode(stringExpr);
        super.visit(stringExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(TernaryExpression ternaryExpr) {
        pushASTNode(ternaryExpr);
        super.visit(ternaryExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(ThisExpression thisExpr) {
        pushASTNode(thisExpr);
        super.visit(thisExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(TypeCastExpression castExpr) {
        pushASTNode(castExpr);
        super.visit(castExpr);
        popASTNode();
        return null;
    }

    @Override
    public Object visit(UnaryExpression unaryExpr) {
        pushASTNode(unaryExpr);
        super.visit(unaryExpr);
        popASTNode();
        return null;
    }

}
