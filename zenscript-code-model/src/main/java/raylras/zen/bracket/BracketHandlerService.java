package raylras.zen.bracket;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.model.CompilationEnvironment;
import raylras.zen.util.Watcher;

import java.io.IOException;
import java.io.Reader;
import java.net.ConnectException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class BracketHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(BracketHandlerService.class);

    private final CompilationEnvironment env;
    private List<BracketHandlerMirror> mirrors;

    public BracketHandlerService(CompilationEnvironment env) {
        this.env = env;
    }

    public List<BracketHandlerMirror> getMirrorsLocal() {
        if (mirrors == null) {
            loadMirrorsFromJson();
        }
        return mirrors;
    }

    public Collection<BracketHandlerEntry> getEntriesLocal() {
        return getMirrorsLocal().stream()
                .flatMap(mirror -> mirror.entries().stream())
                .toList();
    }

    public BracketHandlerEntry queryEntryRemote(String validExpr) {
        var watcher = Watcher.watch(() -> {
            try {
                return RpcClient.queryEntryProperties(validExpr);
            } catch (ConnectException e) {
                logger.warn("Failed to query remote <{}>, make sure your Minecraft instance is running", validExpr);
            } catch (IOException | ExecutionException | InterruptedException e) {
                logger.error("Failed to query remote <{}>, {}", validExpr, e.getMessage());
            }
            return null;
        });
        if (watcher.isResultPresent()) {
            logger.info("Query remote <{}> [{}]", validExpr, watcher.getElapsedMillis());
            return new BracketHandlerEntry(watcher.getResult());
        } else {
            return new BracketHandlerEntry(Collections.emptyMap());
        }
    }

    public CompilationEnvironment getEnv() {
        return env;
    }

    private static final Gson GSON = createGson();

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

    private void loadMirrorsFromJson() {
        env.getGeneratedRoot()
                .map(root -> root.resolve("brackets.json"))
                .filter(Files::exists)
                .ifPresent(jsonPath -> {
                    Watcher<List<BracketHandlerMirror>> watcher = Watcher.watch(() -> {
                        try (Reader reader = Files.newBufferedReader(jsonPath)) {
                            return GSON.fromJson(reader, new TypeToken<>() {});
                        } catch (IOException e) {
                            logger.error("Failed to load bracket handler mirrors from {}", jsonPath.getFileName(), e);
                            return null;
                        }
                    });
                    if (watcher.isResultPresent()) {
                        mirrors = watcher.getResult();
                        logger.info("Load bracket handler mirrors from {} [{}]", jsonPath.getFileName(), watcher.getElapsedMillis());
                    }
                });
    }

}
