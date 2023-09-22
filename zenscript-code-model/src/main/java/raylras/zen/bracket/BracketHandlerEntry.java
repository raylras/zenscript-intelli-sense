package raylras.zen.bracket;

import org.eclipse.lsp4j.jsonrpc.messages.Either;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record BracketHandlerEntry(Map<String, Either<String, List<String>>> properties) {

    public Optional<Either<String, List<String>>> get(String key) {
        return Optional.ofNullable(properties.get(key));
    }

    public Optional<String> getAsString(String key) {
        return get(key).map(it -> it.isLeft() ? it.getLeft() : it.getRight().toString());
    }

    public Optional<List<String>> getAsStringList(String key) {
        return get(key).map(it -> it.isRight() ? it.getRight() : List.of(it.getLeft()));
    }

}
