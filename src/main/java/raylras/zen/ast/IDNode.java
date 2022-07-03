package raylras.zen.ast;

import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.ast.visit.NodeVisitor;

import java.util.List;

public class IDNode extends BaseNode {

    private final String name;

    public IDNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T> T accept(NodeVisitor<? extends T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public List<? extends Node> getChildren() {
        return List.of();
    }

    @Override
    public String toString() {
        return name;
    }

    public static IDNode of(ZenScriptParser.IdentifierContext ctx) {
        if (ctx == null) return null;
        IDNode id = new IDNode(ctx.getText());
        id.setRange(Range.of(ctx));
        return id;
    }

    public static IDNode of(ZenScriptParser.ReferenceContext ctx) {
        if (ctx == null) return null;
        IDNode id = new IDNode(ctx.getText());
        id.setRange(Range.of(ctx));
        return id;
    }

}
