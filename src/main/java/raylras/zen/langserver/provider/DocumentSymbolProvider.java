package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolKind;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.Listener;
import raylras.zen.code.parser.ZenScriptParser;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.Ranges;
import raylras.zen.util.Stack;

import java.util.ArrayList;
import java.util.List;

public final class DocumentSymbolProvider {

    private DocumentSymbolProvider() {}

    public static List<DocumentSymbol> documentSymbol(CompilationUnit unit, DocumentSymbolParams params) {
        DocumentSymbolListener listener = new DocumentSymbolListener();
        unit.accept(listener);
        return listener.getTopLevelSymbolList();
    }

    private static final class DocumentSymbolListener extends Listener {
        private final List<DocumentSymbol> topLevelSymbolList = new ArrayList<>();
        private final Stack<DocumentSymbol> stack = new ArrayStack<>();

        @Override
        public void enterFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
            push(ctx, ctx.simpleName(), SymbolKind.Function);
        }

        @Override
        public void exitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
            pop();
        }

        @Override
        public void enterExpandFunctionDeclaration(ZenScriptParser.ExpandFunctionDeclarationContext ctx) {
            push(ctx, ctx.DOLLAR(), SymbolKind.Function);
        }

        @Override
        public void exitExpandFunctionDeclaration(ZenScriptParser.ExpandFunctionDeclarationContext ctx) {
            pop();
        }

        @Override
        public void enterClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
            push(ctx, ctx.simpleNameOrPrimitiveType(), SymbolKind.Class);
        }

        @Override
        public void exitClassDeclaration(ZenScriptParser.ClassDeclarationContext ctx) {
            pop();
        }

        @Override
        public void enterConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
            push(ctx, ctx.ZEN_CONSTRUCTOR(), SymbolKind.Constructor);
        }

        @Override
        public void exitConstructorDeclaration(ZenScriptParser.ConstructorDeclarationContext ctx) {
            pop();
        }

        @Override
        public void enterVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
            push(ctx, ctx.simpleName(), SymbolKind.Variable);
        }

        @Override
        public void exitVariableDeclaration(ZenScriptParser.VariableDeclarationContext ctx) {
            pop();
        }

        @Override
        public void enterFunctionExpr(ZenScriptParser.FunctionExprContext ctx) {
            push(ctx, ctx.FUNCTION(), SymbolKind.Function);
        }

        @Override
        public void exitFunctionExpr(ZenScriptParser.FunctionExprContext ctx) {
            pop();
        }

        private void push(ParseTree cst, ParseTree name, SymbolKind kind) {
            Range range = Ranges.toLSPRange(cst);
            Range selectionRange = Ranges.toLSPRange(name);
            String text = name.getText();
            push(new DocumentSymbol(text, kind, range, selectionRange));
        }

        private void push(ParseTree cst, String name, SymbolKind kind) {
            Range range = Ranges.toLSPRange(cst);
            push(new DocumentSymbol(name, kind, range, range));
        }

        private void push(DocumentSymbol symbol) {
            if (isTopLevel()) {
                addToTopLevelSymbolList(symbol);
            } else {
                addToCurrentSymbolChildren(symbol);
            }
            stack.push(symbol);
        }

        private void pop() {
            stack.pop();
        }

        private boolean isTopLevel() {
            return stack.isEmpty();
        }

        private void addToTopLevelSymbolList(DocumentSymbol symbol) {
            topLevelSymbolList.add(symbol);
        }

        private void addToCurrentSymbolChildren(DocumentSymbol symbol) {
            DocumentSymbol parent = stack.peek();
            if (parent.getChildren() == null) {
                parent.setChildren(new ArrayList<>());
            }
            parent.getChildren().add(symbol);
        }

        private List<DocumentSymbol> getTopLevelSymbolList() {
            return topLevelSymbolList;
        }
    }

}
