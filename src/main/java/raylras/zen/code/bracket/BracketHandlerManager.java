package raylras.zen.code.bracket;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.eclipse.lsp4j.CompletionItem;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.Type;
import raylras.zen.rpc.RpcClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author youyihj
 */
public class BracketHandlerManager {
    private final List<BracketHandler> bracketHandlers;

    public BracketHandlerManager(List<BracketHandler> bracketHandlers) {
        this.bracketHandlers = bracketHandlers;
    }

    public List<BracketHandler> getBracketHandlers() {
        return bracketHandlers;
    }

    public void complete(String text, List<CompletionItem> completionItems) {
        if (text.startsWith("item:")) {
            text = text.substring(5);
        }
        for (BracketHandler bracketHandler : bracketHandlers) {
            bracketHandler.complete(text, completionItems);
        }
    }

    public Type getType(String text, CompilationEnvironment env) {
        if (text.startsWith("item:")) {
            text = text.substring(5);
        }
        for (BracketHandler bracketHandler : bracketHandlers) {
            if (bracketHandler.has(text)) {
                return bracketHandler.getType(env);
            }
        }
        String typeName = RpcClient.queryBracketHandler(text).getOrDefault("type", "null");
        if (Objects.equals(typeName, "null")) {
            return AnyType.INSTANCE;
        } else {
            return env.getClassTypeMap().get(typeName);
        }
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
