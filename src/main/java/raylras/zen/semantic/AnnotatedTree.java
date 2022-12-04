package raylras.zen.semantic;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import raylras.zen.semantic.scope.Scope;
import raylras.zen.semantic.symbol.Symbol;

public class AnnotatedTree {

    private final String name;
    private final ParseTreeProperty<Scope> nodeToScope;
    private final ParseTreeProperty<Symbol> nodeToSymbol;
    private final ParseTree parseTree;

    public AnnotatedTree(String name, ParseTree parseTree) {
        this.name = name;
        this.nodeToScope = new ParseTreeProperty<>();
        this.nodeToSymbol = new ParseTreeProperty<>();
        this.parseTree = parseTree;
    }

    public String getName() {
        return name;
    }

    public Scope getScopeOfNode(ParseTree node) {
        return nodeToScope.get(node);
    }

    public void bindNodeToScope(ParseTree node, Scope scope) {
        nodeToScope.put(node, scope);
    }

    public void bindNodeToSymbol(ParseTree node, Symbol symbol) {
        nodeToSymbol.put(node, symbol);
    }

    public Symbol findSymbolInNode(ParseTree node, String name) {
        Scope scope = findEncloseScopeOfNode(node);
        if (scope == null) {
            return null;
        } else {
            // TODO raylras: check the global scope
            // TODO raylras: check library scripts
            return scope.findSymbol(name);
        }
    }

    public Scope findEncloseScopeOfNode(ParseTree node) {
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

}
