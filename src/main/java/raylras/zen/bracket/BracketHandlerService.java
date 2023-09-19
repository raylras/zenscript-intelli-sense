package raylras.zen.bracket;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.CompilationEnvironment;

import java.io.IOException;
import java.io.Reader;
import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BracketHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(BracketHandlerService.class);

    private final CompilationEnvironment env;
    private List<BracketHandlerMirror> mirrors;

    public BracketHandlerService(CompilationEnvironment env) {
        this.env = env;
    }

    public Optional<BracketHandlerMirror> queryMirror(String partBracketHandlerExpr) {
        if (mirrors == null) {
            loadFromJson();
        }
        return mirrors.stream()
                .filter(mirror -> partBracketHandlerExpr.matches(mirror.regex()))
                .findFirst();
    }

    public BracketHandlerEntry queryEntryDynamic(String fullBracketHandlerExpr) {
        Map<String, Object> properties;
        try {
            properties = RpcClient.queryEntryDynamic(fullBracketHandlerExpr);
        } catch (ConnectException e) {
            logger.warn("Failed to query <{}>, make sure your Minecraft instance is running", fullBracketHandlerExpr);
            properties = Collections.emptyMap();
        } catch (Exception e) {
            logger.error("Failed to query <{}>: {}", fullBracketHandlerExpr, e.getMessage());
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
        Path jsonPath = env.getGeneratedRoot().resolve("brackets.json");
        if (Files.isRegularFile(jsonPath) || Files.isReadable(jsonPath)) {
            try (Reader reader = Files.newBufferedReader(jsonPath)) {
                mirrors = GSON.fromJson(reader, new TypeToken<>() {});
            } catch (IOException e) {
                logger.error("Failed to open brackets.json", e);
            }
        }
    }

}
