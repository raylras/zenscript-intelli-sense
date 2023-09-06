package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import raylras.zen.code.Visitor;
import raylras.zen.code.parser.ZenScriptParser.*;
import raylras.zen.langserver.Document;
import raylras.zen.util.ArrayStack;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Ranges;
import raylras.zen.util.Stack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class DocumentSymbolProvider {

    private DocumentSymbolProvider() {}

    public static CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(Document doc, DocumentSymbolParams params) {
        return doc.getUnit().map(unit -> CompletableFuture.supplyAsync(() -> {
            DocumentSymbolVisitor visitor = new DocumentSymbolVisitor();
            unit.getParseTree().accept(visitor);
            return visitor.topLevelSymbolList.stream()
                    .map(Either::<SymbolInformation, DocumentSymbol>forRight)
                    .toList();
        })).orElseGet(DocumentSymbolProvider::empty);
    }

    public static CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> empty() {
        return CompletableFuture.completedFuture(null);
    }

    private static final class DocumentSymbolVisitor extends Visitor<DocumentSymbol> {
        private final List<DocumentSymbol> topLevelSymbolList = new ArrayList<>();
        private final Stack<DocumentSymbol> stack = new ArrayStack<>();

        @Override
        public DocumentSymbol visitFunctionDeclaration(FunctionDeclarationContext ctx) {
            return enter(ctx, ctx.simpleName(), SymbolKind.Function);
        }

        @Override
        public DocumentSymbol visitClassDeclaration(ClassDeclarationContext ctx) {
            return enter(ctx, ctx.simpleNameOrPrimitiveType(), SymbolKind.Class);
        }

        @Override
        public DocumentSymbol visitExpandFunctionDeclaration(ExpandFunctionDeclarationContext ctx) {
            return enter(ctx, ctx.simpleName(), SymbolKind.Function);
        }

        @Override
        public DocumentSymbol visitConstructorDeclaration(ConstructorDeclarationContext ctx) {
            return enter(ctx, ctx.ZEN_CONSTRUCTOR(), SymbolKind.Constructor);
        }

        @Override
        public DocumentSymbol visitVariableDeclaration(VariableDeclarationContext ctx) {
            return enter(ctx, ctx.simpleName(), SymbolKind.Variable);
        }

        @Override
        public DocumentSymbol visitFunctionExpr(FunctionExprContext ctx) {
            return enter(ctx, ctx.FUNCTION(), SymbolKind.Function);
        }

        private DocumentSymbol enter(RuleNode enclose, ParseTree selection, SymbolKind kind) {
            DocumentSymbol symbol = toDocumentSymbol(enclose, selection, kind);
            if (push(symbol)) {
                visitChildren(enclose);
                pop();
            }
            return symbol;
        }

        private DocumentSymbol toDocumentSymbol(ParseTree enclose, ParseTree selection, SymbolKind kind) {
            return toDocumentSymbol(enclose, selection, CSTNodes.getText(selection), kind);
        }

        private DocumentSymbol toDocumentSymbol(ParseTree enclose, ParseTree selection, String name, SymbolKind kind) {
            Range encloseRange = Ranges.toLspRange(enclose);
            Range selectionRange = Ranges.toLspRange(selection);
            return new DocumentSymbol(name, kind, encloseRange, selectionRange);
        }

        private boolean push(DocumentSymbol symbol) {
            if (symbol.getName().isEmpty()) {
                return false;
            }
            if (isTopLevel()) {
                addToTopLevelSymbolList(symbol);
            } else {
                addToCurrentSymbolChildren(symbol);
            }
            stack.push(symbol);
            return true;
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
    }

}
