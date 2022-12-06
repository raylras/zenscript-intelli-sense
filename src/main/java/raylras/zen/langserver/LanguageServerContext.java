package raylras.zen.langserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Place to store all global objects.
 */
public class LanguageServerContext {

    private final Map<Class<?>, Object> objects = new HashMap<>();

    public <V> void put(Class<V> clazz, V value) {
        objects.put(clazz, value);
    }

    @SuppressWarnings("unchecked")
    public <V> V get(Class<V> clazz) {
        return (V) objects.get(clazz);
    }

}
