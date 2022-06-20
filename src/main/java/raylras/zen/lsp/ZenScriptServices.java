package raylras.zen.lsp;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.ast.CompileUnit;
import raylras.zen.ast.ScriptNode;
import raylras.zen.lsp.provider.DocumentSymbolProvider;
import raylras.zen.lsp.provider.SemanticTokensProvider;
import raylras.zen.util.URIUtils;

import java.io.StringReader;
import java.util.Collections;
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
        client.logMessage(new MessageParams(MessageType.Info, message));
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {

    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        compileUnit.compile(URIUtils.create(params.getTextDocument().getUri()), new StringReader(params.getContentChanges().get(0).getText()));
        client.refreshSemanticTokens();
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        compileUnit.refresh(URIUtils.create(params.getTextDocument().getUri()));
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
        compileUnit.refresh(URIUtils.create(params.getTextDocument().getUri()));
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
        return CompletableFuture.completedFuture(Either.forLeft(Collections.emptyList()));
    }


    @Override
    public CompletableFuture<SignatureHelp> signatureHelp(SignatureHelpParams params) {
        return CompletableFuture.completedFuture(new SignatureHelp());
    }


    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        return CompletableFuture.completedFuture(Either.forLeft(Collections.emptyList()));
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        if (compileUnit == null) return CompletableFuture.completedFuture(Collections.emptyList());
        ScriptNode scriptNode = compileUnit.getScriptNode(URIUtils.create(params.getTextDocument().getUri()));
        return CompletableFuture.completedFuture(new DocumentSymbolProvider().provideDocumentSymbol(scriptNode));
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        if (compileUnit == null) return CompletableFuture.completedFuture(new SemanticTokens(Collections.emptyList()));
        ScriptNode scriptNode = compileUnit.getScriptNode(URIUtils.create(params.getTextDocument().getUri()));
        return CompletableFuture.completedFuture(new SemanticTokensProvider().provideSemanticTokens(scriptNode));
    }

}
