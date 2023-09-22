package raylras.zen.bracket;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.type.AnyType;
import raylras.zen.model.type.Type;
import raylras.zen.util.PackageTree;
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
    private BracketHandlerMirror itemMirror;

    public BracketHandlerService(CompilationEnvironment env) {
        this.env = env;
    }

    public List<BracketHandlerMirror> getMirrorsLocal() {
        if (mirrors == null) {
            loadFromJson();
        }
        return mirrors;
    }

    public BracketHandlerMirror getItemMirror() {
        if (itemMirror == null) {
            itemMirror = getMirrorsLocal().stream()
                    .filter(it -> "crafttweaker.item.IItemStack".equals(it.typeQualifiedName()))
                    .findFirst()
                    .orElseThrow();
        }
        return itemMirror;
    }

    public Type queryType(String validExpr) {
        if (validExpr.startsWith("item:")) {
            return env.getClassTypeMap().get(getItemMirror().typeQualifiedName());
        }

        for (BracketHandlerMirror mirror : getMirrorsLocal()) {
            if (mirror.entries().get(validExpr).hasElement()) {
                return env.getClassTypeMap().get(mirror.typeQualifiedName());
            }
        }

        BracketHandlerEntry remoteEntry = queryEntryRemote(validExpr);
        return remoteEntry.getAsString("_type")
                .map(it -> (Type) env.getClassTypeMap().get(it))
                .orElse(AnyType.INSTANCE);
    }

    public Collection<BracketHandlerEntry> queryEntriesLocal(String partialExpr) {
        Collection<BracketHandlerEntry> entries = new ArrayList<>();
        if (partialExpr.startsWith("item:")) {
            collectEntriesFromMirror(partialExpr.substring(5), getItemMirror(), entries);
        } else {
            for (BracketHandlerMirror mirror : getMirrorsLocal()) {
                collectEntriesFromMirror(partialExpr, mirror, entries);
            }
        }
        return entries;
    }

    public Collection<BracketHandlerEntry> getEntriesLocal() {
        return getMirrorsLocal().stream()
                .flatMap(mirror -> mirror.entries().elements().stream())
                .toList();
    }

    public Optional<BracketHandlerMirror> queryMirrorLocal(String expr) {
        return getMirrorsLocal().stream()
                .filter(mirror -> expr.matches(mirror.regex()))
                .findFirst();
    }

    public BracketHandlerEntry queryEntryRemote(String validExpr) {
        Map<String, Object> rawProperties;
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            rawProperties = RpcClient.queryEntryProperties(validExpr);
            sw.stop();
            logger.info("Query remote <{}> [{}ms]", validExpr, sw.getFormattedMillis());
        } catch (ConnectException e) {
            logger.warn("Failed to query <{}>, make sure your Minecraft instance is running", validExpr);
            rawProperties = Collections.emptyMap();
        } catch (Exception e) {
            logger.error("Failed to query <{}>: {}", validExpr, e.getMessage());
            rawProperties = Collections.emptyMap();
        }
        Map<String, Either<String, List<String>>> properties = new HashMap<>();
        rawProperties.forEach((key, value) -> {
            if (value instanceof List<?> list) {
                properties.put(key, Either.forRight(list.stream().map(Objects::toString).toList()));
            } else {
                properties.put(key, Either.forLeft(String.valueOf(value)));
            }
        });
        return new BracketHandlerEntry(properties);
    }

    private void collectEntriesFromMirror(String partialExpr, BracketHandlerMirror mirror, Collection<BracketHandlerEntry> entries) {
        mirror.entries().complete(partialExpr).values().forEach(it -> entries.addAll(it.elements()));
    }

    private static final Gson GSON = createGson();

    private static Gson createGson() {
        JsonDeserializer<BracketHandlerMirror> mirrorDeserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            String qualifiedName = jsonObject.get("type").getAsString();
            String regex = jsonObject.get("regex").getAsString();
            List<BracketHandlerEntry> entries = context.deserialize(jsonObject.get("entries"), new TypeToken<List<BracketHandlerEntry>>() {}.getType());
            PackageTree<BracketHandlerEntry> entriesTree = new PackageTree<>(":");
            for (BracketHandlerEntry entry : entries) {
                entry.getAsString("_id").ifPresent(id -> entriesTree.put(id, entry));
            }
            return new BracketHandlerMirror(qualifiedName, regex, entriesTree);
        };
        JsonDeserializer<BracketHandlerEntry> entryDeserializer = (json, typeOfT, context) -> {
            Map<String, JsonElement> map = json.getAsJsonObject().asMap();
            Map<String, Either<String, List<String>>> properties = new HashMap<>();
            map.forEach((key, value) -> {
                if (value instanceof JsonArray array) {
                    List<String> strings = new ArrayList<>();
                    array.forEach(element -> strings.add(element.getAsString()));
                    properties.put(key, Either.forRight(strings));
                } else {
                    properties.put(key, Either.forLeft(value.getAsString()));
                }
            });
            return new BracketHandlerEntry(properties);
        };
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
