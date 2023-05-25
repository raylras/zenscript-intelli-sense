package raylras.zen.util;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

public class ParseTreeProperty<V> {

    private final Map<ParseTree, V> annotations = new IdentityHashMap<>();

    public V get(ParseTree node) {
        return annotations.get(node);
    }

    public void put(ParseTree node, V value) {
        annotations.put(node, value);
    }

    public V removeFrom(ParseTree node) {
        return annotations.remove(node);
    }

	public Collection<V> values() {
		return annotations.values();
	}

}
