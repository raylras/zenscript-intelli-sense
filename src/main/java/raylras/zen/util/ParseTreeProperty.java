package raylras.zen.util;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

public class ParseTreeProperty<V> {

    private final Map<ParseTree, V> properties = new IdentityHashMap<>();

    public V get(ParseTree cst) {
        return properties.get(cst);
    }

    public void put(ParseTree cst, V value) {
        properties.put(cst, value);
    }

	public Collection<V> values() {
		return properties.values();
	}

}
