package raylras.zen.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PackageTree<V> {
    private final Map<String, PackageTree<V>> subTrees = new HashMap<>();
    private V element;
    
    public static <V> PackageTree<V> of(Map<String, V> map) {
        PackageTree<V> tree = new PackageTree<>();
        map.forEach(tree::put);
        return tree;
    }

    public void put(String path, V value) {
        PackageTree<V> leaf = this;
        Function<String, PackageTree<V>> treeCreator = it -> new PackageTree<>();
        for (String s : path.split("\\.")) {
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

    public PackageTree<V> get(String path) {
        PackageTree<V> node = this;
        for (String s : path.split("\\.")) {
            node = node.subTrees.get(s);
            if (node == null) {
                return PackageTree.of(Collections.emptyMap());
            }
        }
        return node;
    }
}
