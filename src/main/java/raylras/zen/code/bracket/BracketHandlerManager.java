package raylras.zen.code.bracket;

import com.google.gson.*;
import org.eclipse.lsp4j.CompletionItem;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.type.AnyType;
import raylras.zen.code.type.ClassType;
import raylras.zen.code.type.Type;
import raylras.zen.rpc.RpcClient;

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
        String bracketTypeName = RpcClient.queryBracketHandler(text).get("type");
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
