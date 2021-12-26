package raylras.zen.symbol;

import org.antlr.v4.runtime.Token;
import raylras.zen.scope.Scope;
import raylras.zen.type.Type;
import raylras.zen.type.TypeFunction;

import java.util.List;

// Function or Method
public class SymbolFunction extends SymbolBase<TypeFunction> {

    public SymbolFunction(Scope parent, Token token) {
        super(parent, token);
    }

    public SymbolFunction(Scope parent, Token token, Type returnType, List<Type> argumentTypes) {
        super(parent, token);
        this.setType(new TypeFunction(token.getText(), returnType, argumentTypes));
    }

    public Type getReturnType() {
        return getType().getReturnType();
    }

    public List<Type> getArgumentTypes() {
        return getType().getArgumentTypes();
    }

    // methods can be overloaded, we should use signatures to ensure uniqueness
    public String getSignature() {
        return getType().getSignature();
    }

    // functions can not be overloaded, we should use symbolName
    @Override
    public String getSymbolName() {
        return super.getSymbolName();
    }

    @Override
    public String toString() {
        return getType() == null ? getSymbolName() : getSignature();
    }

}
