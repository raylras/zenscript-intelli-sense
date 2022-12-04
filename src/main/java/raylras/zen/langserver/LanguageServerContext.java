package raylras.zen.langserver;

import org.eclipse.lsp4j.services.LanguageClient;
import raylras.zen.project.ZenProjectManager;

import java.util.HashMap;
import java.util.Map;

/**
 * place to store contexts for global access
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

    public ZenProjectManager projectManager() {
        return ZenProjectManager.getInstance(this);
    }

    public LanguageClientLogger clientLogger() {
        return LanguageClientLogger.getInstance(this);
    }

    public LanguageClient languageClient() {
        return get(LanguageClient.class);
    }

}
