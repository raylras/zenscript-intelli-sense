package raylras.zen.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PackageTree<V> {
    private final Map<String, PackageTree<V>> subTrees = new HashMap<>();
    private V element;
    private final String delimiter;

    public PackageTree(String delimiter) {
        this.delimiter = delimiter;
    }

    public static <V> PackageTree<V> of(String delimiter, Map<String, V> map) {
        PackageTree<V> tree = new PackageTree<>(delimiter);
        map.forEach(tree::put);
        return tree;
    }

    public void put(String path, V value) {
        PackageTree<V> leaf = this;
        Function<String, PackageTree<V>> treeCreator = it -> new PackageTree<>(delimiter);
        for (String s : path.split(delimiter)) {
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
        for (String s : path.split(delimiter)) {
            node = node.subTrees.get(s);
            if (node == null) {
                return PackageTree.of(delimiter, Collections.emptyMap());
            }
        }
        return node;
    }
}
