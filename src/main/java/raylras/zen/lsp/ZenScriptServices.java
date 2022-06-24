package raylras.zen.lsp;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.ast.CompileUnit;
import raylras.zen.ast.Node;
import raylras.zen.ast.Position;
import raylras.zen.ast.ScriptNode;
import raylras.zen.lsp.provider.DocumentSymbolProvider;
import raylras.zen.lsp.provider.SemanticTokensProvider;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ZenScriptServices implements TextDocumentService, WorkspaceService {

    private ZenScriptLanguageServer server;
    private LanguageClient client;
    private WorkspaceFolder workspace;
    private CompileUnit compileUnit;

    public ZenScriptLanguageServer getServer() {
        return server;
    }

    public void setServer(ZenScriptLanguageServer server) {
        this.server = server;
    }

    public LanguageClient getClient() {
        return client;
    }

    public void setClient(LanguageClient client) {
        this.client = client;
    }

    public WorkspaceFolder getWorkspace() {
        return workspace;
    }

    public void setWorkspace(WorkspaceFolder workspace) {
        this.workspace = workspace;
    }

    public CompileUnit getCompileUnit() {
        return compileUnit;
    }

    public void setCompileUnit(CompileUnit compileUnit) {
        this.compileUnit = compileUnit;
    }

    public void info(String message) {
        if (client == null || message == null) return;
        client.logMessage(new MessageParams(MessageType.Info, message));
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        try {
            compileUnit.compile(params.getTextDocument().getUri(), new StringReader(params.getTextDocument().getText()));
        } catch (Exception e) {
            info(e.getMessage());
        }
        client.refreshSemanticTokens();
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        try {
            compileUnit.compile(params.getTextDocument().getUri(), new StringReader(params.getContentChanges().get(0).getText()));
        } catch (Exception e) {
            info(e.getMessage());
        }
        client.refreshSemanticTokens();
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        compileUnit.refresh(params.getTextDocument().getUri());
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
        try {
            compileUnit.refresh(params.getTextDocument().getUri());
        } catch (Exception e) {
            info(e.getMessage());
        }
        client.refreshSemanticTokens();
    }

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {

    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {

    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        ScriptNode scriptNode = compileUnit.getScriptNode(params.getTextDocument().getUri());
        Position pos = Position.of(params.getPosition());
        Hover result = scriptNode.getNodeAtPosition(pos)
                .map(Node::getType)
                .map(Object::toString)
                .map(content -> new Hover(new MarkupContent(MarkupKind.MARKDOWN, content)))
                .orElse(null);
//        if (node instanceof BracketHandler bh) {
//            String uri = Paths.get("src/main/resources/grass.png").toUri().toString();
//            String content = "![](" + uri + ")";
//            return CompletableFuture.completedFuture(new Hover(new MarkupContent(MarkupKind.MARKDOWN, content)));
//        }
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public CompletableFuture<SignatureHelp> signatureHelp(SignatureHelpParams params) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        try {
            ScriptNode scriptNode = compileUnit.getScriptNode(params.getTextDocument().getUri());
            return CompletableFuture.completedFuture(new DocumentSymbolProvider().provideDocumentSymbol(scriptNode));
        } catch (Exception e) {
            info(e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        try {
            ScriptNode scriptNode = compileUnit.getScriptNode(params.getTextDocument().getUri());
            return CompletableFuture.completedFuture(new SemanticTokensProvider().provideSemanticTokens(scriptNode));
        } catch (Exception e) {
            info(e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }

}
