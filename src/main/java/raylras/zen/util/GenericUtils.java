package raylras.zen.util;

import java.util.List;

@SuppressWarnings("unchecked")
public class GenericUtils {
    public static <T, U extends T> List<T> castToSuperExplicitly(List<U> list) {
        return (List<T>) list;
    }
}
