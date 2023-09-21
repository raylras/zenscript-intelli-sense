package raylras.zen.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PackageTree<V> {
    private final Map<String, PackageTree<V>> subTrees = new HashMap<>();
    private V element;
    private final String delimiter;
    private final Pattern delimiterRegex;

    public PackageTree(String delimiter) {
        this.delimiter = delimiter;
        this.delimiterRegex = Pattern.compile(delimiter, Pattern.LITERAL);
    }

    public static <V> PackageTree<V> of(String delimiter, Map<String, V> map) {
        PackageTree<V> tree = new PackageTree<>(delimiter);
        map.forEach(tree::put);
        return tree;
    }

    public void put(String path, V value) {
        PackageTree<V> leaf = this;
        Function<String, PackageTree<V>> treeCreator = it -> new PackageTree<>(delimiter);
        for (String s : delimiterRegex.split(path)) {
            leaf = leaf.subTrees.computeIfAbsent(s, treeCreator);
        }
        leaf.element = value;
    }

    public V getElement() {
        return element;
    }

    public boolean hasElement() {
        return getElement() != null;
    }

    public Map<String, PackageTree<V>> getSubTrees() {
        return subTrees;
    }

    public boolean isEmpty() {
        return subTrees.isEmpty();
    }

    public PackageTree<V> get(String path) {
        PackageTree<V> node = this;
        for (String s : delimiterRegex.split(path)) {
            node = node.subTrees.get(s);
            if (node == null) {
                return PackageTree.of(delimiter, Collections.emptyMap());
            }
        }
        return node;
    }

    public Map<String, PackageTree<V>> complete(String text) {
        String toComplete;
        int lastDelimiterPos = text.lastIndexOf(delimiter);
        Map<String, PackageTree<V>> members;
        if (lastDelimiterPos != -1) {
            String completed = text.substring(0, lastDelimiterPos);
            members = this.get(completed).getSubTrees();
            if (lastDelimiterPos == text.length() - 1) {
                toComplete = "";
            } else {
                toComplete = text.substring(lastDelimiterPos + 1);
            }
        } else {
            members = this.getSubTrees();
            toComplete = text;
        }
        return members.entrySet().stream()
                .filter(it -> it.getKey().startsWith(toComplete))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
