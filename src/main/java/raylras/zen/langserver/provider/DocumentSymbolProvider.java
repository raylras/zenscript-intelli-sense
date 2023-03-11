package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolKind;
import raylras.zen.code.Ranges;
import raylras.zen.code.SourceUnit;
import raylras.zen.code.tree.*;
import raylras.zen.code.tree.stmt.VariableDeclaration;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentSymbolProvider extends TreeVisitor {

    private final List<DocumentSymbol> topLevelSymbols = new ArrayList<>();
    private final Stack<DocumentSymbol> stack = new ArrayStack<>();

    public static List<DocumentSymbol> documentSymbol(SourceUnit sourceUnit, DocumentSymbolParams params) {
        if (sourceUnit == null) return Collections.emptyList();
        if (sourceUnit.ast == null) sourceUnit.updateAll(null);
        DocumentSymbolProvider provider = new DocumentSymbolProvider();
        sourceUnit.ast.accept(provider);
        return provider.topLevelSymbols;
    }

    private void push(SimpleName name, TreeNode node, SymbolKind kind) {
        if (name == null || node == null) return;
        Range range = Ranges.toLSPRange(node.range);
        Range selectionRange = Ranges.toLSPRange(name.range);
        push(new DocumentSymbol(name.literal, kind, range, selectionRange));
    }

    private void push(DocumentSymbol symbol) {
        if (isTopLevel()) {
            topLevelSymbols.add(symbol);
        } else {
            DocumentSymbol parent = stack.peek();
            if (parent.getChildren() == null) {
                parent.setChildren(new ArrayList<>());
            }
            parent.getChildren().add(symbol);
        }
        stack.push(symbol);
    }

    private void pop() {
        stack.pop();
    }

    private boolean isTopLevel() {
        return stack.isEmpty();
    }

    @Override
    public boolean visit(ClassDeclaration node) {
        push(node.name, node, SymbolKind.Class);
        return true;
    }

    @Override
    public void afterVisit(ClassDeclaration node) {
        pop();
    }

    @Override
    public boolean visit(ConstructorDeclaration node) {
        push(node.name, node, SymbolKind.Function);
        return true;
    }

    @Override
    public void afterVisit(ConstructorDeclaration node) {
        pop();
    }


    @Override
    public boolean visit(FunctionDeclaration node) {
        push(node.name, node, SymbolKind.Function);
        return true;
    }

    @Override
    public void afterVisit(FunctionDeclaration node) {
        pop();
    }


    @Override
    public boolean visit(ParameterDeclaration node) {
        push(node.name, node, SymbolKind.Variable);
        return true;
    }

    @Override
    public void afterVisit(ParameterDeclaration node) {
        pop();
    }


    @Override
    public boolean visit(VariableDeclaration node) {
        push(node.name, node, SymbolKind.Variable);
        return true;
    }

    @Override
    public void afterVisit(VariableDeclaration node) {
        pop();
    }

}
