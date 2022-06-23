package raylras.zen.ast;

import raylras.zen.ast.type.Type;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;

public interface Node {

    <T> T accept(NodeVisitor<? extends T> visitor);

    Range getRange();

    void setRange(Range range);

    Type getType();

    void setType(Type type);

    List<? extends Node> getChildren();

}
