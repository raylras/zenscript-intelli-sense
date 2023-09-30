package raylras.zen.bracket;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.model.CompilationEnvironment;
import raylras.zen.util.StopWatch;

import java.io.IOException;
import java.io.Reader;
import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
        Map<String, List<String>> properties;
        try {
            StopWatch sw = StopWatch.createAndStart();
            properties = RpcClient.queryEntryProperties(validExpr);
            sw.stop();
            logger.info("Query remote <{}> [{}]", validExpr, sw.getFormattedMillis());
        } catch (ConnectException e) {
            logger.warn("Failed to query remote <{}>, make sure your Minecraft instance is running", validExpr);
            properties = Collections.emptyMap();
        } catch (Exception e) {
            logger.error("Failed to query remote <{}>: {}", validExpr, e.getMessage());
            properties = Collections.emptyMap();
        }
        return new BracketHandlerEntry(properties);
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
            json.getAsJsonObject().asMap().forEach((key, value)-> {
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
        Path jsonPath = env.getGeneratedRoot().resolve("brackets.json");
        try (Reader reader = Files.newBufferedReader(jsonPath)) {
            StopWatch sw = StopWatch.createAndStart();
            mirrors = GSON.fromJson(reader, new TypeToken<>() {});
            sw.stop();
            logger.info("Load bracket handler mirrors from {} [{}]", jsonPath.getFileName(), sw.getFormattedMillis());
        } catch (IOException e) {
            logger.error("Failed to load bracket handler mirrors from {}", jsonPath.getFileName(), e);
        }
    }

}
