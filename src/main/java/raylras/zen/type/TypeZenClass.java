package raylras.zen.type;

import raylras.zen.lsp.antlr.ZenScriptParser;

import java.util.ArrayList;
import java.util.List;

public class TypeZenClass implements Type {

    private final List<String> packageName;

    public TypeZenClass(List<String> packageName) {
        this.packageName = packageName;
    }

    public TypeZenClass(ZenScriptParser.TypeClassContext context) {
        packageName = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.join(".", packageName);
    }

}
