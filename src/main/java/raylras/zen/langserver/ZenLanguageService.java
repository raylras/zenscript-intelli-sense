package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.code.CompilationUnit;
import raylras.zen.langserver.provider.CompletionProvider;
import raylras.zen.langserver.provider.SemanticTokensProvider;
import raylras.zen.util.Utils;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ZenLanguageService implements TextDocumentService, WorkspaceService {

    public ZenLanguageServer server;
    public CompilationEnvironment env;

    public ZenLanguageService(ZenLanguageServer server) {
        this.server = server;
    }

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        checkEnvironment(params.getTextDocument().getUri());
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        CompilationUnit unit = getUnit(params.getTextDocument().getUri());
        String source = params.getContentChanges().get(0).getText();
        unit.load(source);
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        CompilationUnit unit = getUnit(params.getTextDocument().getUri());
        SemanticTokens data = SemanticTokensProvider.semanticTokensFull(unit, params);
        return CompletableFuture.completedFuture(data);
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        return CompletableFuture.completedFuture(null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        CompilationUnit unit = getUnit(params.getTextDocument().getUri());
        List<CompletionItem> data = CompletionProvider.completion(unit, params);
        return CompletableFuture.completedFuture(Either.forLeft(data));
    }

    @Override
    public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
        return CompletableFuture.completedFuture(null);
    }

    /* End Text Document Service */

    /* Workspace Service */

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {
    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
        params.getChanges().forEach(event -> {
            Path documentPath = Utils.toPath(event.getUri());
            CompilationUnit unit;
            switch (event.getType()) {
                case Created:
                    unit = new CompilationUnit(documentPath, env);
                    unit.load();
                    env.addUnit(unit);
                    break;
                case Changed:
                    unit = env.getUnit(documentPath);
                    unit.load();
                    break;
                case Deleted:
                    env.removeUnit(documentPath);
                    break;
            }
        });
    }

    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
        params.getEvent().getRemoved().forEach(workspace -> {
            server.log("Removed workspace: " + workspace);
        });
        params.getEvent().getAdded().forEach(workspace -> {
            server.log("Added workspace: " + workspace);
        });
    }

    /* End Workspace Service */

    private void checkEnvironment(String uri) {
        Path documentPath = Utils.toPath(uri);
        if (env != null && Objects.equals(documentPath, env.compilationRoot)) {
            return;
        }
        createEnvironment(documentPath);
    }

    private void createEnvironment(Path documentPath) {
        Path compilationRoot = Utils.findUpwards(documentPath, "scripts");
        if (compilationRoot == null)
            compilationRoot = documentPath;
        env = new CompilationEnvironment(compilationRoot);
        env.load();
    }

    private CompilationUnit getUnit(String uri) {
        Path documentPath = Utils.toPath(uri);
        return env.getUnit(documentPath);
    }

}
