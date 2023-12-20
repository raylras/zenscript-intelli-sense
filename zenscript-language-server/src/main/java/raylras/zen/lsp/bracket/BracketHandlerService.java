package raylras.zen.lsp.bracket;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.model.CompilationEnvironment;
import raylras.zen.util.l10n.L10N;

import java.io.IOException;
import java.io.Reader;
import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class BracketHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(BracketHandlerService.class);

    private List<BracketHandlerMirror> mirrors;

    public static BracketHandlerService getInstance(CompilationEnvironment env) {
        return BRACKET_SERVICES.computeIfAbsent(env, e -> new BracketHandlerService(env));
    }

    public List<BracketHandlerMirror> getMirrorsLocal() {
        return mirrors;
    }

    public Collection<BracketHandlerEntry> getEntriesLocal() {
        return getMirrorsLocal().stream()
                .flatMap(mirror -> mirror.entries().stream())
                .toList();
    }

    public BracketHandlerEntry getEntryRemote(String expr) {
        try {
            Map<String, List<String>> properties = RpcClient.getEntryPropertiesRemote(expr);
            return new BracketHandlerEntry(properties);
        } catch (ConnectException e) {
            logger.warn("Failed to query <{}>: {}", expr, e.getMessage());
            String message = L10N.format("minecraft_instance_not_running", expr);
            return new BracketHandlerEntry(Map.of("_errorMessage", List.of(message)));
        } catch (IOException | ExecutionException | InterruptedException | TimeoutException e) {
            logger.error("Failed to query <{}>: {}", expr, e.getMessage());
            String message = L10N.format("query_bracket_handler_failed", expr, e.getMessage());
            return new BracketHandlerEntry(Map.of("_errorMessage", List.of(message)));
        }
    }


    /* Internals */

    private static final WeakHashMap<CompilationEnvironment, BracketHandlerService> BRACKET_SERVICES = new WeakHashMap<>(1);
    private static final Gson GSON = createGson();

    private BracketHandlerService(CompilationEnvironment env) {
        Path path = env.getGeneratedRoot().resolve("brackets.json");
        if (Files.exists(path)) {
            loadMirrorsFromJson(path);
        } else {
            logger.error("brackets.json not found for env: {}", env);
        }
    }

    private void loadMirrorsFromJson(Path jsonPath) {
        try (Reader reader = Files.newBufferedReader(jsonPath)) {
            mirrors = GSON.fromJson(reader, new TypeToken<>() {});
        } catch (IOException e) {
            logger.error("Failed to load bracket handler mirrors from {}", jsonPath, e);
            mirrors = List.of();
        }
    }

    private static Gson createGson() {
        JsonDeserializer<BracketHandlerMirror> mirrorDeserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            String regex = jsonObject.get("regex").getAsString();
            List<BracketHandlerEntry> entries = context.deserialize(jsonObject.get("entries"), new TypeToken<List<BracketHandlerEntry>>() {}.getType());
            return new BracketHandlerMirror(type, regex, entries);
        };
        JsonDeserializer<BracketHandlerEntry> entryDeserializer = (json, typeOfT, context) -> {
            Map<String, List<String>> properties = new HashMap<>();
            json.getAsJsonObject().asMap().forEach((key, value) -> {
                if (value instanceof JsonArray array) {
                    properties.put(key, context.deserialize(array, new TypeToken<List<String>>() {}.getType()));
                } else if (value instanceof JsonPrimitive primitive) {
                    properties.put(key, List.of(primitive.getAsString()));
                } else {
                    logger.warn("Unexpected type of value: {}", value);
                }
            });
            return new BracketHandlerEntry(properties);
        };
        return new GsonBuilder()
                .registerTypeAdapter(BracketHandlerMirror.class, mirrorDeserializer)
                .registerTypeAdapter(BracketHandlerEntry.class, entryDeserializer)
                .create();
    }

}
