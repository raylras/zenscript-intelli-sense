package raylras.zen.code.type;

public class StringType extends ClassType {

    public static final StringType INSTANCE = new StringType();

    private StringType() {
        super("string");
    }

    @Override
    public Kind getKind() {
        return Kind.STRING;
    }

}
