package raylras.zen.bracket;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public record BracketHandlerEntry(Map<String, ?> properties) {

    public void ifPresent(String key, Consumer<Object> action) {
        Object value = properties.get(key);
        if (value != null) {
            action.accept(value);
        }
    }

    public Optional<Object> get(String key) {
        return Optional.ofNullable(properties.get(key));
    }

    public Optional<String> getAsString(String key) {
        return get(key).map(String::valueOf);
    }

}
