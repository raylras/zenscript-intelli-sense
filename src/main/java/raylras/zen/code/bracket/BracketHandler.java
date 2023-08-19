package raylras.zen.code.bracket;

import com.google.gson.*;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.type.ClassType;
import raylras.zen.util.PackageTree;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author youyihj
 */
public class BracketHandler {
    private final PackageTree<JsonElement> members = new PackageTree<>(":");
    private final String typeName;

    public BracketHandler(String typeName) {
        this.typeName = typeName;
    }

    public void complete(String text, List<CompletionItem> completionItems) {
        members.complete(text).forEach((key, subTree) -> {
            CompletionItem completionItem = new CompletionItem(key);
            if (subTree.hasElement()) {
                completionItem.setKind(CompletionItemKind.Value);
                completionItem.setDetail(getCompletionItemDetails(subTree.getElement()));
            } else {
                completionItem.setKind(CompletionItemKind.Module);
            }
            completionItems.add(completionItem);
        });
    }

    public boolean has(String member) {
        return members.get(member).hasElement();
    }

    private String getCompletionItemDetails(JsonElement json) {
        if (json.isJsonObject()) {
            JsonElement name = json.getAsJsonObject().get("name");
            if (name != null) {
                return name.getAsString();
            }
        }
        return null;
    }

    public ClassType getType(CompilationEnvironment environment) {
        return environment.getClassTypeMap().get(typeName);
    }

    public static final class Deserializer implements JsonDeserializer<BracketHandler> {

        @Override
        public BracketHandler deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            BracketHandler bracketHandler = new BracketHandler(jsonObject.get("type").getAsString());
            for (JsonElement content : jsonObject.get("contents").getAsJsonArray()) {
                if (content.isJsonPrimitive()) {
                    bracketHandler.members.put(content.getAsString(), JsonNull.INSTANCE);
                } else {
                    bracketHandler.members.put(content.getAsJsonObject().get("id").getAsString(), content);
                }
            }
            return bracketHandler;
        }
    }
}
