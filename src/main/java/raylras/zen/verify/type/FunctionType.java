package raylras.zen.verify.type;

public class FunctionType extends AbstractType {

    private final String signature;

    public FunctionType(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return signature;
    }

}
