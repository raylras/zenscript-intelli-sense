package raylras.zen.model.symbol;

import raylras.zen.model.CompilationEnvironment;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.SymbolProvider;

import java.util.*;

public class SymbolTree {
    private final String qualifiedName;
    private final List<Symbol> symbols = new ArrayList<>();
    private final Map<String, SymbolTree> subTrees = new HashMap<>();

    public SymbolTree(String qualifiedName, CompilationEnvironment env) {
        this.qualifiedName = qualifiedName;
        this.symbols.add(SymbolFactory.createPackageSymbol(this, env));
    }

    public void addUnitTopLevelSymbols(CompilationUnit unit) {
        SymbolTree leaf = this;
        for (String s : unit.getQualifiedName().split("\\.")) {
            SymbolTree subTree = leaf.subTrees.get(s);
            if (subTree == null) {
                String leafName = leaf.getQualifiedName();
                subTree = new SymbolTree(leafName.isEmpty() ? s : leafName + "." + s, unit.getEnv());
                leaf.subTrees.put(s, subTree);
            }
            leaf = subTree;
        }
        leaf.symbols.clear();
        for (Symbol topLevelSymbol : unit.getTopLevelSymbols()) {
            if (topLevelSymbol.isStatic() || topLevelSymbol instanceof FunctionSymbol) {
                leaf.symbols.add(topLevelSymbol);
            }
            if (topLevelSymbol instanceof ClassSymbol classSymbol) {
                leaf.symbols.add(topLevelSymbol);
                SymbolProvider staticMembers = classSymbol.filter(Symbol::isStatic);
                if (staticMembers.size() != 0) {
                    SymbolTree classSymbolTree = new SymbolTree(classSymbol.getQualifiedName(), unit.getEnv());
                    for (Symbol declaredMember : classSymbol.getDeclaredMembers()) {
                        if (declaredMember.isStatic()) {
                            classSymbolTree.symbols.add(declaredMember);
                        }
                    }
                    leaf.subTrees.put(classSymbol.getName(), classSymbolTree);
                }
            }
        }
    }

    public List<Symbol> get(String qualifiedName) {
        SymbolTree leaf = this;
        for (String s : qualifiedName.split("\\.")) {
            leaf = leaf.subTrees.get(s);
            if (leaf == null) {
                return Collections.emptyList();
            }
        }
        return leaf.getSymbols();
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public Map<String, SymbolTree> getSubTrees() {
        return subTrees;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }
}
