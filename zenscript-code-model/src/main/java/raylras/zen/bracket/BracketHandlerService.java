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
            loadFromJson();
        }
        return mirrors;
    }

    public Collection<BracketHandlerEntry> getEntriesLocal() {
        return getMirrorsLocal().stream()
                .flatMap(mirror -> mirror.entries().stream())
                .toList();
    }

    public Optional<BracketHandlerMirror> queryMirrorLocal(String expr) {
        return getMirrorsLocal().stream()
                .filter(mirror -> expr.matches(mirror.regex()))
                .findFirst();
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
            String qualifiedName = jsonObject.get("type").getAsString();
            String regex = jsonObject.get("regex").getAsString();
            List<BracketHandlerEntry> entries = context.deserialize(jsonObject.get("entries"), new TypeToken<List<BracketHandlerEntry>>() {}.getType());
            return new BracketHandlerMirror(qualifiedName, regex, entries);
        };
        JsonDeserializer<BracketHandlerEntry> entryDeserializer = (json, typeOfT, context) -> new BracketHandlerEntry(json.getAsJsonObject().asMap());
        return new GsonBuilder()
                .registerTypeAdapter(BracketHandlerMirror.class, mirrorDeserializer)
                .registerTypeAdapter(BracketHandlerEntry.class, entryDeserializer)
                .create();
    }

    private void loadFromJson() {
        StopWatch sw = new StopWatch();
        sw.start();
        Path jsonPath = env.getGeneratedRoot().resolve("brackets.json");
        if (Files.isRegularFile(jsonPath) || Files.isReadable(jsonPath)) {
            try (Reader reader = Files.newBufferedReader(jsonPath)) {
                mirrors = GSON.fromJson(reader, new TypeToken<>() {});
            } catch (IOException e) {
                logger.error("Failed to open brackets.json", e);
            }
        }
        sw.stop();
        logger.info("Load mirrors from {} [{}ms]", jsonPath.getFileName(), sw.getFormattedMillis());
    }

}
