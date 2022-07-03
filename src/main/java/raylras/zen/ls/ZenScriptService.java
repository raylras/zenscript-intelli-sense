package raylras.zen.ls;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.ast.CompileUnit;
import raylras.zen.ls.provider.*;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ZenScriptService implements TextDocumentService, WorkspaceService {

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
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new CompletionProvider().provideCompletion(params, compileUnit);
            } catch (Exception e) {
                info(e.getMessage());
                return null;
            }
        });
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new HoverProvider().provideHover(params, compileUnit);
            } catch (Exception e) {
                info(e.getMessage());
                return null;
            }
        });
    }

    @Override
    public CompletableFuture<SignatureHelp> signatureHelp(SignatureHelpParams params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new SignatureHelpProvider().provideSignatureHelp(params, compileUnit);
            } catch (Exception e) {
                info(e.getMessage());
                return null;
            }
        });
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new DefinitionProvider().provideDefinition(params, compileUnit);
            } catch (Exception e) {
                info(e.getMessage());
                return null;
            }
        });
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new DocumentSymbolProvider().provideDocumentSymbol(params, compileUnit);
            } catch (Exception e) {
                info(e.getMessage());
                return null;
            }
        });
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new SemanticTokensProvider().provideSemanticTokens(params, compileUnit);
            } catch (Exception e) {
                info(e.getMessage());
                return null;
            }
        });
    }

}
