package raylras.zen.code.symbol;

import raylras.zen.code.annotation.Annotation;
import raylras.zen.code.type.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class BuiltinSymbol extends Symbol {

    private final Type type;

    public BuiltinSymbol(String name, Type type) {
        super(name, null, null);
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Kind getKind() {
        return Kind.BUILT_IN;
    }

    @Override
    public Modifier getModifier() {
        return Modifier.NONE;
    }

    @Override
    public Optional<Annotation> getDeclaredAnnotation(String header) {
        return Optional.empty();
    }

    @Override
    public java.util.List<Annotation> getDeclaredAnnotations() {
        return Collections.emptyList();
    }

    public static class List {
        private final java.util.List<Symbol> list = new ArrayList<>();

        public static List builder() {
            return new List();
        }

        public List add(String name, Type type) {
            list.add(new BuiltinSymbol(name, type));
            return this;
        }

        public java.util.List<Symbol> build() {
            return list;
        }
    }

}
