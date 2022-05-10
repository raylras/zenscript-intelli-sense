package raylras.zen.ast;

public class CrossScriptReferenceNode extends ReferenceNode {

    public CrossScriptReferenceNode(String scriptReference) {
        super(scriptReference);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitCrossScriptReference(this);
    }

}
