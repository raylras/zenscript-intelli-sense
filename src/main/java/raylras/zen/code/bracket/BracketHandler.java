package raylras.zen.code.bracket;

import com.google.gson.*;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.type.ClassType;
import raylras.zen.util.PackageTree;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public class BracketHandler {
    private PackageTree<JsonElement> members = new PackageTree<JsonElement>().setDelimiter(":");
    private final CompilationEnvironment environment;
    private final String typeName;

    public BracketHandler(CompilationEnvironment environment, String typeName) {
        this.environment = environment;
        this.typeName = typeName;
    }

    public void complete(String text, List<CompletionItem> completionItems) {
        String toComplete;
        String completed;
        int lastDotPosition = text.lastIndexOf(':');
        Map<String, PackageTree<JsonElement>> bracketMembers;
        if (lastDotPosition != -1) {
            completed = text.substring(0, lastDotPosition);
            bracketMembers = members.get(completed).getSubTrees();
            if (lastDotPosition == text.length() - 1) {
                toComplete = "";
            } else {
                toComplete = text.substring(lastDotPosition + 1);
            }
        } else {
            bracketMembers = members.getSubTrees();
            toComplete = text;
        }
        bracketMembers.forEach((key, subTree) -> {
            if (key.startsWith(toComplete)) {
                CompletionItem completionItem = new CompletionItem(key);
                if (subTree.hasElement()) {
                    completionItem.setKind(CompletionItemKind.Value);
                    completionItem.setDetail(getCompletionItemDetails(subTree.getElement()));
                } else {
                    completionItem.setKind(CompletionItemKind.Module);
                }
                completionItems.add(completionItem);
            }
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

    public ClassType getType() {
        return environment.getClassTypeMap().get(typeName);
    }

    public static final class Deserializer implements JsonDeserializer<BracketHandler> {

        private final CompilationEnvironment environment;

        public Deserializer(CompilationEnvironment environment) {
            this.environment = environment;
        }

        @Override
        public BracketHandler deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            BracketHandler bracketHandler = new BracketHandler(environment, jsonObject.get("type").getAsString());
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
