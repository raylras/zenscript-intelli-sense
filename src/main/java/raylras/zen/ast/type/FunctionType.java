package raylras.zen.ast.type;

import java.util.List;
import java.util.stream.Collectors;

public record FunctionType(List<Type> arguments, Type result) implements Type {

    @Override
    public boolean equivalent(Type type) {
        if (type instanceof FunctionType that) {
            // check arguments size
            if (this.arguments.size() != that.arguments.size()) {
                return false;
            }
            // check arguments type
            for (int i = 0; i < arguments.size(); i++) {
                if (!this.arguments.get(i).equivalent(that.arguments.get(i))) {
                    return false;
                }
            }
            // check result type
            return this.result.equivalent(that.result);
        }
        return false;
    }

    @Override
    public String toString() {
        return "function(" + arguments.stream().map(Object::toString).collect(Collectors.joining(",")) + ")" + result;
    }

}
