package raylras.zen.code.type;

import org.antlr.v4.runtime.tree.ParseTree;
import raylras.zen.code.resolve.NameResolver;

public class ErrorType extends NamedType {


    public ErrorType(String name) {
        super(name);
    }

    public ErrorType(ParseTree node) {
        this(NameResolver.resolveName(node));
    }

    @Override
    public Kind getKind() {
        return Kind.NONE;
    }

    @Override
    public String toString() {
        return "ERROR~" + name;
    }
}
