package raylras.zen.lsp;

import raylras.zen.ast.*;
import raylras.zen.ast.expr.*;
import raylras.zen.ast.stmt.*;
import raylras.zen.verify.Environment;

import java.net.URI;
import java.util.*;

// TODO: AST visitor
public class ZenScriptVisitor extends ZenScriptBaseVisitor {

    private class ASTLookupKey {
        private final ASTNode node;

        public ASTLookupKey(ASTNode node) {
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

    private class ASTNodeLookupData {
        public ASTNode parent;
        public URI uri;
    }

    public final Environment env;
    private final Map<URI, List<ASTNode>> astNodeListByURI = new HashMap<>();
    private final Stack<ASTNode> stack = new Stack<>();
    private final Map<ASTLookupKey, ASTNodeLookupData> lookup = new HashMap<>();
    private ScriptNode scriptUnit;


    public ZenScriptVisitor(Environment env) {
        this.env = env;
    }

    public Environment getEnv() {
        return env;
    }

    public Map<URI, List<ASTNode>> getAstNodeListByURI() {
        return astNodeListByURI;
    }

    private void pushASTNode(ASTNode node) {
        URI uri = scriptUnit.getURI();
        astNodeListByURI.get(uri).add(node);

        ASTNodeLookupData data = new ASTNodeLookupData();
        data.uri = uri;
        if (stack.size() > 0) {
            data.parent = stack.lastElement();
        }
        lookup.put(new ASTLookupKey(node), data);

        stack.add(node);
    }

    private void popASTNode() {
        stack.pop();
    }

    //
    // ASTVisitor overrides
    //

    @Override
    public void visitAlias(AliasNode node) {

    }

    @Override
    public void visitBlock(BlockNode node) {

    }

    @Override
    public void visitClassReference(ClassReferenceNode node) {

    }

    @Override
    public void visitConstructor(ConstructorNode node) {

    }

    @Override
    public void visitCrossScriptReference(CrossScriptReferenceNode node) {

    }

    @Override
    public void visitField(FieldNode node) {

    }

    @Override
    public void visitFunction(FunctionNode node) {

    }

    @Override
    public void visitIdentifier(IdentifierNode node) {

    }

    @Override
    public void visitImport(ImportNode node) {

    }

    @Override
    public void visitParameter(ParameterNode node) {

    }

    @Override
    public void visitReference(ReferenceNode node) {

    }

    @Override
    public void visitScriptNode(ScriptNode node) {
        this.scriptUnit = node;

        pushASTNode(node);

        astNodeListByURI.put(node.getURI(), new ArrayList<>());
        node.getImportNodeList().forEach(this::visitImport);
        node.getFunctionNodeList().forEach(this::visitFunction);
        node.getZenClassNodeList().forEach(this::visitZenClass);
        node.getStatementList().forEach(this::visitStatement);

        popASTNode();

        this.scriptUnit = null;
        stack.clear();
    }

    @Override
    public void visitType(TypeNode node) {
    }

    @Override
    public void visitVariable(VariableNode node) {

    }

    @Override
    public void visitZenClass(ZenClassNode node) {
        pushASTNode(node);

        popASTNode();
    }

    @Override
    public void visitBlockStatement(BlockStatement node) {

    }

    @Override
    public void visitBreakStatement(BreakStatement node) {

    }

    @Override
    public void visitContinueStatement(ContinueStatement node) {

    }

    @Override
    public void visitExpressionStatement(ExpressionStatement node) {

    }

    @Override
    public void visitForeachStatement(ForeachStatement node) {

    }

    @Override
    public void visitIfStatement(IfStatement node) {

    }

    @Override
    public void visitImportStatement(ImportStatement node) {

    }

    @Override
    public void visitReturnStatement(ReturnStatement node) {

    }

    @Override
    public void visitStatement(Statement node) {

    }

    @Override
    public void visitVarStatement(VarStatement node) {

    }

    @Override
    public void visitWhileStatement(WhileStatement node) {

    }

    @Override
    public void visitArgumentsExpression(ArgumentsExpression node) {

    }

    @Override
    public void visitArrayLiteralExpression(ArrayLiteralExpression node) {

    }

    @Override
    public void visitAssignmentExpression(AssignmentExpression node) {

    }

    @Override
    public void visitBinaryExpression(BinaryExpression node) {

    }

    @Override
    public void visitBooleanLiteralExpression(BooleanLiteralExpression node) {

    }

    @Override
    public void visitBracketHandlerExpression(BracketHandlerExpression node) {

    }

    @Override
    public void visitExpression(Expression node) {

    }

    @Override
    public void visitFunctionExpression(FunctionExpression node) {

    }

    @Override
    public void visitIdentifierExpression(IdentifierExpression node) {

    }

    @Override
    public void visitMapEntryExpression(MapEntryExpression node) {

    }

    @Override
    public void visitMapLiteralExpression(MapLiteralExpression node) {

    }

    @Override
    public void visitMemberAccessExpression(MemberAccessExpression node) {

    }

    @Override
    public void visitMemberIndexExpression(MemberIndexExpression node) {

    }

    @Override
    public void visitNullExpression(NullExpression node) {

    }

    @Override
    public void visitNumberLiteralExpression(NumberLiteralExpression node) {

    }

    @Override
    public void visitParensExpression(ParensExpression node) {

    }

    @Override
    public void visitRangeExpression(RangeExpression node) {

    }

    @Override
    public void visitStringLiteralExpression(StringLiteralExpression node) {

    }

    @Override
    public void visitTernaryExpression(TernaryExpression node) {

    }

    @Override
    public void visitThisExpression(ThisExpression node) {

    }

    @Override
    public void visitTypeCastExpression(TypeCastExpression node) {

    }

    @Override
    public void visitUnaryExpression(UnaryExpression node) {

    }

}
