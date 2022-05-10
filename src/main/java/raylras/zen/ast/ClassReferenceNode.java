package raylras.zen.ast;

public class ClassReferenceNode extends ReferenceNode {

    public ClassReferenceNode(String className) {
        super(className);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitClassReference(this);
    }

}
