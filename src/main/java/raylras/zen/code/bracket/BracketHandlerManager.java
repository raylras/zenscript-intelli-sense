package raylras.zen.code.bracket;

import com.google.gson.*;
import raylras.zen.bracket.BracketHandlerService;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youyihj
 */
public class BracketHandlerManager {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(BracketHandler.class, new BracketHandler.Deserializer())
            .registerTypeAdapter(BracketHandlerManager.class, new Deserializer())
            .create();

    private final List<BracketHandler> bracketHandlers;
    private final BracketHandler itemBracketHandler;

    public BracketHandlerManager(List<BracketHandler> bracketHandlers) {
        this.bracketHandlers = bracketHandlers;
        this.itemBracketHandler = bracketHandlers.stream()
                .filter(it -> "crafttweaker.item.IItemStack".equals(it.getTypeName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No item bracket handler found"));
    }

    public List<BracketHandler> getBracketHandlers() {
        return bracketHandlers;
    }

    public BracketHandler getItemBracketHandler() {
        return itemBracketHandler;
    }

    public Type getType(String text, CompilationEnvironment env) {
        if (text.startsWith("item:")) {
            return itemBracketHandler.getType(env);
        }
        for (BracketHandler bracketHandler : bracketHandlers) {
            if (bracketHandler.has(text)) {
                return bracketHandler.getType(env);
            }
        }
        String bracketTypeName = BracketHandlerService.queryEntityDynamic(text).getAsString("type").orElse(null);
        ClassType classType = env.getClassTypeMap().get(bracketTypeName);
        return (classType != null) ? classType : AnyType.INSTANCE;
    }

    public static class Deserializer implements JsonDeserializer<BracketHandlerManager> {

        @Override
        public BracketHandlerManager deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            List<BracketHandler> bracketHandlers = new ArrayList<>();
            for (JsonElement jsonElement : json.getAsJsonArray()) {
                bracketHandlers.add(context.deserialize(jsonElement, BracketHandler.class));
            }
            return new BracketHandlerManager(bracketHandlers);
        }
    }
}
