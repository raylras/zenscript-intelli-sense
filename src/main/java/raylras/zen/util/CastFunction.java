package raylras.zen.util;

import java.util.function.Function;

public class CastFunction<T> implements Function<Object, T> {
    private final Class<T> clazz;

    public CastFunction(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <T> CastFunction<T> of(Class<T> clazz) {
        return new CastFunction<>(clazz);
    }

    @Override
    public T apply(Object o) {
        if (clazz.isInstance(o)) {
            return clazz.cast(o);
        }
        return null;
    }
}
