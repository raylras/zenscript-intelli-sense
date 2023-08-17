package raylras.zen.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PackageTree<V> {
    private final Map<String, PackageTree<V>> subTrees = new HashMap<>();
    private V element;
    private String delimiter = "\\.";
    
    public static <V> PackageTree<V> of(Map<String, V> map) {
        PackageTree<V> tree = new PackageTree<>();
        map.forEach(tree::put);
        return tree;
    }
    public void put(String path, V value) {
        PackageTree<V> leaf = this;
        Function<String, PackageTree<V>> treeCreator = it -> new PackageTree<>();
        for (String s : path.split(delimiter)) {
            leaf = leaf.subTrees.computeIfAbsent(s, treeCreator);
        }
        leaf.element = value;
    }

    public PackageTree<V> setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
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
        for (String s : path.split(delimiter)) {
            node = node.subTrees.get(s);
            if (node == null) {
                return PackageTree.of(Collections.emptyMap());
            }
        }
        return node;
    }
}
