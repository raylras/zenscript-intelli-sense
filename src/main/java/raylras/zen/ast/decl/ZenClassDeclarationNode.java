package raylras.zen.ast.decl;

import raylras.zen.ast.ASTNode;
import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.type.*;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * zenClass MyClass { var a; zenConstructor(){ stmt; } function fn(){ stmt;} }
 */
public class ZenClassDeclarationNode extends ASTNode implements Declaration, TopLevel {

    private Identifier identifier;
    private List<ASTNode> members;

    public ZenClassDeclarationNode() {
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public List<ASTNode> getMembers() {
        return members;
    }

    public void setMembers(List<ASTNode> members) {
        this.members = members;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof Identifier) {
            identifier = (Identifier) node;
        } else if (node instanceof Variable || node instanceof Constructor || node instanceof Function) {
            if (members == null) {
                members = new ArrayList<>();
            }
            members.add(node);
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(identifier, members);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
