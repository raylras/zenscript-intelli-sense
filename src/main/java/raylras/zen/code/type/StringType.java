package raylras.zen.code.type;

public class StringType extends Type {

    public static final StringType INSTANCE = new StringType();

    @Override
    public Kind getKind() {
        return Kind.STRING;
    }

}
