package raylras.zen.lsp.bracket;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record BracketHandlerEntry(Map<String, List<String>> properties) {

    public List<String> get(String key) {
        List<String> values = properties.get(key);
        return (values != null) ? values : Collections.emptyList();
    }

    public Optional<String> getFirst(String key) {
        List<String> values = get(key);
        return !values.isEmpty() ? Optional.of(values.get(0)) : Optional.empty();
    }

}
