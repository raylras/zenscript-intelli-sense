package raylras.zen.model.symbol;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.CompilationUnit;

import java.util.*;

public class SymbolTree {
    private final String qualifiedName;
    private final List<Symbol> symbols = new ArrayList<>();
    private final Map<String, SymbolTree> children = new HashMap<>();
    private final CompilationEnvironment env;

    private boolean isPackage = false;

    public SymbolTree(String name, SymbolTree parent, CompilationEnvironment env) {
        if (parent == null || parent.getQualifiedName().isEmpty()) {
            this.qualifiedName = name;
        } else {
            this.qualifiedName = parent.getQualifiedName() + "." + name;
        }
        this.env = env;
    }

    public void addUnitTopLevelSymbols(CompilationUnit unit) {
        if (!unit.isGenerated()) {
            SymbolTree symbolTree = switchChildren(unit.getQualifiedName(), null);
            unit.getTopLevelSymbols().forEach(symbolTree::addMember);
        } else {
            if (unit.getTopLevelSymbols().size() == 1 && unit.getTopLevelSymbols().get(0) instanceof ClassSymbol classSymbol) {
                SymbolTree symbolTree = switchChildren(classSymbol.getQualifiedName(), classSymbol);
                symbolTree.symbols.add(classSymbol);
                for (Symbol staticMembers : classSymbol.filter(Symbol::isStatic)) {
                    symbolTree.addMember(staticMembers);
                }
            }
        }
    }

    public List<Symbol> get(String qualifiedName) {
        SymbolTree leaf = this;
        for (String s : qualifiedName.split("\\.")) {
            leaf = leaf.children.get(s);
            if (leaf == null) {
                return Collections.emptyList();
            }
        }
        return leaf.getSymbols();
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public Map<String, SymbolTree> getChildren() {
        return children;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    private void markPackage() {
        if (!isPackage) {
            isPackage = true;
            symbols.add(SymbolFactory.createPackageSymbol(this, env));
        }
    }

    private void addMember(Symbol symbol) {
        children.computeIfAbsent(symbol.getName(), it ->
                new SymbolTree(it, this, env)
        ).symbols.add(symbol);
    }

    private SymbolTree switchChildren(String path, Symbol symbol) {
        String[] split = path.split("\\.");
        SymbolTree current = this;
        for (int i = 0; i < split.length; i++) {
            SymbolTree child = current.children.get(split[i]);
            if (child == null) {
                child = new SymbolTree(split[i], current, env);
                current.children.put(split[i], child);
            }
            if (symbol == null) {
                child.markPackage();
            } else {
                if (i != split.length - 1) {
                    child.markPackage();
                } else {
                    child.symbols.add(symbol);
                }
            }
            current = child;
        }
        return current;
    }
}
