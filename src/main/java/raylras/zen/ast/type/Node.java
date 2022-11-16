package raylras.zen.ast.type;

import raylras.zen.ast.ASTNodeVisitor;
import raylras.zen.ast.TextRange;

import java.util.List;

public interface Node {

    TextRange getTextRange();

    List<Node> getChildren();

    <T> T accept(ASTNodeVisitor<? extends T> visitor);

}
