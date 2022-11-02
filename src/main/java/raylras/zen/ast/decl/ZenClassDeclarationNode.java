package raylras.zen.ast.decl;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.IdentifierNode;
import raylras.zen.ast.TopLevelNode;
import raylras.zen.ast.ASTNodeVisitor;

import java.util.List;

/**
 * zenClass MyClass { var a; zenConstructor(){ stmt; } function fn(){ stmt;} }
 */
public class ZenClassDeclarationNode extends ASTNode implements DeclarationNode, TopLevelNode {

    private final IdentifierNode identifier;
    private final List<VariableDeclarationNode> fields;
    private final List<ConstructorDeclarationNode> constructors;
    private final List<FunctionDeclarationNode> functions;

    public ZenClassDeclarationNode(
            IdentifierNode identifier,
            List<VariableDeclarationNode> fields,
            List<ConstructorDeclarationNode> constructors,
            List<FunctionDeclarationNode> functions) {
        this.identifier = identifier;
        this.fields = fields;
        this.constructors = constructors;
        this.functions = functions;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "zenClass " + identifier + " {...}";
    }

}
