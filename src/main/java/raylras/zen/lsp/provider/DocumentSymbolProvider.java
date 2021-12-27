package raylras.zen.lsp.provider;

import org.antlr.v4.runtime.Token;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import raylras.zen.lsp.antlr.ZenScriptParser;
import raylras.zen.lsp.antlr.ZenScriptParserBaseVisitor;
import raylras.zen.util.PosUtil;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DocumentSymbolProvider extends ZenScriptParserBaseVisitor<DocumentSymbol> {

    private final ZenScriptParser.ScriptContext scriptContext;
    private final List<DocumentSymbol> result;
    private final Stack<DocumentSymbol> symbolStack;

    public DocumentSymbolProvider(ZenScriptParser.ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
        this.result = new LinkedList<>();
        this.symbolStack = new Stack<>();
    }

    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> provideDocumentSymbol(DocumentSymbolParams params) {
        visitScript(scriptContext);
        return CompletableFuture.completedFuture(result.stream().map(Either::<SymbolInformation, DocumentSymbol>forRight).collect(Collectors.toList()));
    }

    @Override
    public DocumentSymbol visitFunctionDeclaration(ZenScriptParser.FunctionDeclarationContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        Token tEnd = ctx.block().BRACE_CLOSE().getSymbol();

        Range range = new Range(PosUtil.getPosition(tName), PosUtil.getPosition(tEnd));
        DocumentSymbol symbol = new DocumentSymbol(tName.getText(), SymbolKind.Function, range, range);

        DocumentSymbol parent = symbolStack.empty() ? null : symbolStack.peek();
        if (parent != null) {
            if (parent.getChildren() == null) { parent.setChildren(new LinkedList<>()); }
            parent.getChildren().add(symbol);
            return null;
        }

        symbolStack.push(symbol);
        visitBlock(ctx.block());
        symbolStack.pop();

        result.add(symbol);
        return null;
    }

    @Override
    public DocumentSymbol visitZenClassDeclaration(ZenScriptParser.ZenClassDeclarationContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        Token tEnd = ctx.classBody().BRACE_CLOSE().getSymbol();

        Range range = new Range(PosUtil.getPosition(tName), PosUtil.getPosition(tEnd));
        DocumentSymbol symbol = new DocumentSymbol(tName.getText(), SymbolKind.Class, range, range);

        DocumentSymbol parent = symbolStack.empty() ? null : symbolStack.peek();
        if (parent != null) {
            if (parent.getChildren() == null) { parent.setChildren(new LinkedList<>()); }
            parent.getChildren().add(symbol);
            return null;
        }

        symbolStack.push(symbol);
        visitClassBody(ctx.classBody());
        symbolStack.pop();

        result.add(symbol);
        return null;
    }

    @Override
    public DocumentSymbol visitConstructor(ZenScriptParser.ConstructorContext ctx) {
        String name = "zenConstructor";
        Token tStart = ctx.ZEN_CONSTRUCTOR().getSymbol();
        Token tEnd = ctx.block().BRACE_CLOSE().getSymbol();

        Range range = new Range(PosUtil.getPosition(tStart), PosUtil.getPosition(tEnd));
        DocumentSymbol symbol = new DocumentSymbol(name, SymbolKind.Method, range, range);

        DocumentSymbol parent = symbolStack.empty() ? null : symbolStack.peek();
        if (parent != null) {
            if (parent.getChildren() == null) { parent.setChildren(new LinkedList<>()); }
            parent.getChildren().add(symbol);
            return null;
        }

        symbolStack.push(symbol);
        visitBlock(ctx.block());
        symbolStack.pop();

        result.add(symbol);
        return null;
    }

    @Override
    public DocumentSymbol visitField(ZenScriptParser.FieldContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        Token tEnd = ctx.SEMICOLON().getSymbol();

        Range range = new Range(PosUtil.getPosition(tName), PosUtil.getPosition(tEnd));
        DocumentSymbol symbol = new DocumentSymbol(tName.getText(), SymbolKind.Field, range, range);

        DocumentSymbol parent = symbolStack.empty() ? null : symbolStack.peek();
        if (parent != null) {
            if (parent.getChildren() == null) { parent.setChildren(new LinkedList<>()); }
            parent.getChildren().add(symbol);
            return null;
        }

        result.add(symbol);
        return null;
    }

    @Override
    public DocumentSymbol visitMethod(ZenScriptParser.MethodContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        Token tEnd = ctx.block().BRACE_CLOSE().getSymbol();

        Range range = new Range(PosUtil.getPosition(tName), PosUtil.getPosition(tEnd));
        DocumentSymbol symbol = new DocumentSymbol(tName.getText(), SymbolKind.Method, range, range);

        DocumentSymbol parent = symbolStack.empty() ? null : symbolStack.peek();
        if (parent != null) {
            if (parent.getChildren() == null) { parent.setChildren(new LinkedList<>()); }
            parent.getChildren().add(symbol);
            return null;
        }

        symbolStack.push(symbol);
        visitBlock(ctx.block());
        symbolStack.pop();

        result.add(symbol);
        return null;
    }

    @Override
    public DocumentSymbol visitLocalVariableDeclaration(ZenScriptParser.LocalVariableDeclarationContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        Token tEnd = ctx.SEMICOLON().getSymbol();

        Range range = new Range(PosUtil.getPosition(tName), PosUtil.getPosition(tEnd));
        DocumentSymbol symbol = new DocumentSymbol(tName.getText(), SymbolKind.Variable, range, range);

        DocumentSymbol parent = symbolStack.empty() ? null : symbolStack.peek();
        if (parent != null) {
            if (parent.getChildren() == null) { parent.setChildren(new LinkedList<>()); }
            parent.getChildren().add(symbol);
            return null;
        }

        result.add(symbol);
        return null;
    }

    @Override
    public DocumentSymbol visitGlobalVariableDeclaration(ZenScriptParser.GlobalVariableDeclarationContext ctx) {
        Token tName = ctx.IDENTIFIER().getSymbol();
        Token tEnd = ctx.SEMICOLON().getSymbol();

        Range range = new Range(PosUtil.getPosition(tName), PosUtil.getPosition(tEnd));
        DocumentSymbol symbol = new DocumentSymbol(tName.getText(), SymbolKind.Variable, range, range);

        DocumentSymbol parent = symbolStack.empty() ? null : symbolStack.peek();
        if (parent != null) {
            if (parent.getChildren() == null) { parent.setChildren(new LinkedList<>()); }
            parent.getChildren().add(symbol);
            return null;
        }

        result.add(symbol);
        return null;
    }

    @Override
    public DocumentSymbol visitAnonymousFunction(ZenScriptParser.AnonymousFunctionContext ctx) {
        String name = "<anonymous>";
        Token tStart = ctx.FUNCTION().getSymbol();
        Token tEnd = ctx.block().BRACE_CLOSE().getSymbol();

        Range range = new Range(PosUtil.getPosition(tStart), PosUtil.getPosition(tEnd));
        DocumentSymbol symbol = new DocumentSymbol(name, SymbolKind.Function, range, range);

        DocumentSymbol parent = symbolStack.empty() ? null : symbolStack.peek();
        if (parent != null) {
            if (parent.getChildren() == null) { parent.setChildren(new LinkedList<>()); }
            parent.getChildren().add(symbol);
            return null;
        }

        result.add(symbol);
        return null;
    }

    @Override
    public DocumentSymbol visitBlock(ZenScriptParser.BlockContext ctx) {
        // 4 means LocalVariableDeclaration
        // see ZenScriptParser.g4 statements rule
        ZenScriptParser.StatementsContext context = ctx.statements(4);
        return context == null ? null : visit(context); // visitLocalVariableDeclaration
    }

}
