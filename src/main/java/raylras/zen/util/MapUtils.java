package raylras.zen.util;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MapUtils {

    public static <K, V> void ifPresent(Map<K, V> map, K key, Consumer<V> action) {
        V value = map.get(key);
        if (value != null) {
            action.accept(value);
        }
    }

    public static <K, V> void ifPresent(Map<K, V> map, K key, BiConsumer<K, V> action) {
        V value = map.get(key);
        if (value != null) {
            action.accept(key, value);
        }
    }

}
