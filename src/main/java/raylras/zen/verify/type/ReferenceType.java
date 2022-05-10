package raylras.zen.verify.type;

public class ReferenceType extends AbstractType {

    private final String className;

    public ReferenceType(String typeName) {
        this.className = typeName;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return className;
    }

}
