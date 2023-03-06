package raylras.zen.langserver.provider;

import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolKind;
import raylras.zen.code.Ranges;
import raylras.zen.code.SourceUnit;
import raylras.zen.code.tree.*;
import raylras.zen.code.tree.stmt.VariableDecl;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentSymbolProvider extends GenericTreeVisitor<Object> {

    private final List<DocumentSymbol> topLevelSymbols = new ArrayList<>();
    private final Stack<DocumentSymbol> stack = new ArrayStack<>();

    public static List<DocumentSymbol> documentSymbol(SourceUnit sourceUnit, DocumentSymbolParams params) {
        if (sourceUnit == null)
            return Collections.emptyList();
        if (sourceUnit.ast == null)
            sourceUnit.updateAll(null);
        return new DocumentSymbolProvider().visitCompilationUnit(sourceUnit.ast);
    }

    private void push(Name name, TreeNode node, SymbolKind kind) {
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
    public List<DocumentSymbol> visitCompilationUnit(CompilationUnit node) {
        super.visitCompilationUnit(node);
        return topLevelSymbols;
    }

    @Override
    public Void visitClassDecl(ClassDecl node) {
        if (node== null || node.name == null) return null;
        push(node.name, node, SymbolKind.Class);
        super.visitClassDecl(node);
        pop();
        return null;
    }

    @Override
    public Void visitConstructorDecl(ConstructorDecl node) {
        if (node== null || node.name == null) return null;
        push(node.name, node, SymbolKind.Function);
        super.visitConstructorDecl(node);
        pop();
        return null;
    }

    @Override
    public Void visitFunctionDecl(FunctionDecl node) {
        if (node== null || node.name == null) return null;
        push(node.name, node, SymbolKind.Function);
        super.visitFunctionDecl(node);
        pop();
        return null;
    }

    @Override
    public Void visitParameterDecl(ParameterDecl node) {
        if (node== null || node.name == null) return null;
        push(node.name, node, SymbolKind.Variable);
        super.visitParameterDecl(node);
        pop();
        return null;
    }

    @Override
    public Void visitVariableDecl(VariableDecl node) {
        if (node== null || node.name == null) return null;
        push(node.name, node, SymbolKind.Variable);
        super.visitVariableDecl(node);
        pop();
        return null;
    }

}
