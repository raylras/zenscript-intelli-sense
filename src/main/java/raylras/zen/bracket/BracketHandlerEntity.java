package raylras.zen.bracket;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public record BracketHandlerEntity(Map<String, String> properties) {

    public void ifPresent(String key, Consumer<String> action) {
        String value = properties.get(key);
        if (value != null) {
            action.accept(value);
        }
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(properties.get(key));
    }

}
