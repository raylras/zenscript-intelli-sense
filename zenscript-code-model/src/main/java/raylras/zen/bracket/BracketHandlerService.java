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

    public List<BracketHandlerMirror> getMirrorListLocal() {
        if (mirrors == null) {
            loadMirrorFromJson();
        }
        return mirrors;
    }

    public Collection<BracketHandlerEntry> getEntryListLocal() {
        return getMirrorListLocal().stream()
                .flatMap(mirror -> mirror.entries().stream())
                .toList();
    }

    public BracketHandlerEntry queryEntryRemote(String validExpr) {
        Map<String, Object> properties;
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            properties = RpcClient.queryEntryProperties(validExpr);
            sw.stop();
            logger.info("Query remote <{}> [{}ms]", validExpr, sw.getFormattedMillis());
        } catch (ConnectException e) {
            logger.warn("Failed to query <{}>, make sure your Minecraft instance is running", validExpr);
            properties = Collections.emptyMap();
        } catch (Exception e) {
            logger.error("Failed to query <{}>: {}", validExpr, e.getMessage());
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
            Map<String, Object> properties = new HashMap<>();
            json.getAsJsonObject().asMap().forEach((key, value)-> {
                if (value instanceof JsonArray array) {
                    properties.put(key, context.deserialize(array, new TypeToken<List<String>>() {}.getType()));
                } else if (value instanceof JsonPrimitive primitive) {
                    properties.put(key, primitive.getAsString());
                } else {
                    logger.warn("Unknown type of value: {}", value);
                }
            });
            return new BracketHandlerEntry(properties);
        };
        return new GsonBuilder()
                .registerTypeAdapter(BracketHandlerMirror.class, mirrorDeserializer)
                .registerTypeAdapter(BracketHandlerEntry.class, entryDeserializer)
                .create();
    }

    private void loadMirrorFromJson() {
        Path jsonPath = env.getGeneratedRoot().resolve("brackets.json");
        if (Files.isRegularFile(jsonPath) || Files.isReadable(jsonPath)) {
            try (Reader reader = Files.newBufferedReader(jsonPath)) {
                StopWatch sw = new StopWatch();
                sw.start();
                mirrors = GSON.fromJson(reader, new TypeToken<>() {});
                sw.stop();
                logger.info("Load bracket handler mirror from {} [{}ms]", jsonPath.getFileName(), sw.getFormattedMillis());
            } catch (IOException e) {
                logger.error("Failed to load bracket handler mirror from {}", jsonPath.getFileName(), e);
            }
        }
    }

}
