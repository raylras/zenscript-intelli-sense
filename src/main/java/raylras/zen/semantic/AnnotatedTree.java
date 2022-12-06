package raylras.zen.semantic;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import raylras.zen.semantic.scope.Scope;
import raylras.zen.semantic.symbol.Symbol;
import raylras.zen.semantic.type.Type;

public class AnnotatedTree {

    private final ParseTreeProperty<Scope> nodeToScope;
    private final ParseTreeProperty<Symbol<?>> nodeToSymbol;
    private final ParseTreeProperty<Type> nodeToType;
    private final ParseTree parseTree;
    private final String name;

    public AnnotatedTree(String name, ParseTree parseTree) {
        this.nodeToScope = new ParseTreeProperty<>();
        this.nodeToSymbol = new ParseTreeProperty<>();
        this.nodeToType = new ParseTreeProperty<>();
        this.parseTree = parseTree;
        this.name = name;
    }

    public void bindNodeToScope(ParseTree node, Scope scope) {
        nodeToScope.put(node, scope);
    }

    public void bindNodeToSymbol(ParseTree node, Symbol<?> symbol) {
        nodeToSymbol.put(node, symbol);
    }

    public void bindNodeToType(ParseTree node, Type type) {
        nodeToType.put(node, type);
    }

    public Scope getScopeOfNode(ParseTree node) {
        return nodeToScope.get(node);
    }

    public Type getTypeOfNode(ParseTree node) {
        return nodeToType.get(node);
    }

    public Symbol<?> findSymbolOfNode(ParseTree node, String name) {
        Scope scope = findScopeOfNode(node);
        if (scope == null) {
            return null;
        } else {
            // TODO raylras: check the global scope
            // TODO raylras: check library scripts
            return scope.findSymbol(name);
        }
    }

    public Scope findScopeOfNode(ParseTree node) {
        ParseTree nodeToFind = node;
        Scope result = null;
        while (nodeToFind != null) {
            result = getScopeOfNode(nodeToFind);
            if (result != null) {
                break;
            } else {
                nodeToFind = nodeToFind.getParent();
            }
        }
        return result;
    }

    public ParseTree getParseTree() {
        return parseTree;
    }

    public String getName() {
        return name;
    }

}
