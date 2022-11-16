package raylras.zen.ast;

import raylras.zen.ast.type.Node;
import raylras.zen.ast.type.TopLevel;
import raylras.zen.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class CompilationUnitNode extends ASTNode {

    private String uri;
    private List<TopLevel> members;

    public CompilationUnitNode(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<TopLevel> getMembers() {
        return members;
    }

    public void setMembers(List<TopLevel> members) {
        this.members = members;
    }

    @Override
    public void addChild(ASTNode node) {
        if (node instanceof TopLevel) {
            if (members == null) {
                members = new ArrayList<>();
            }
            members.add((TopLevel) node);
        }
    }

    @Override
    public List<Node> getChildren() {
        return CommonUtils.toChildrenList(members);
    }

    @Override
    public <T> T accept(ASTNodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

}
