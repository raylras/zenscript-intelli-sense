package raylras.zen.langserver;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.code.CompilationContext;
import raylras.zen.code.SourceUnit;
import raylras.zen.langserver.provider.DefinitionProvider;
import raylras.zen.langserver.provider.DocumentSymbolProvider;
import raylras.zen.langserver.provider.HoverProvider;
import raylras.zen.langserver.provider.SemanticTokensProvider;
import raylras.zen.util.Timer;
import raylras.zen.util.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ZenLanguageService implements TextDocumentService, WorkspaceService {

    public ZenLanguageServer server;
    public List<CompilationContext> contexts;

    public ZenLanguageService(ZenLanguageServer server) {
        this.server = server;
        this.contexts = new ArrayList<>();
    }

    /**
     * Gets the source unit with the given URI, null if the URI is invalid.
     * Creating a new one if the source unit does not already exist.
     *
     * @param uri The uri of the source unit.
     * @return The source unit.
     */
    public SourceUnit getSourceUnit(String uri) {
        Path path = Utils.toPath(uri);
        if (!Files.exists(path) || !Files.isRegularFile(path))
            return null;

        CompilationContext context = getCompilationContext(uri);
        if (context == null)
            return null;

        return context.sourceMap.computeIfAbsent(path, p -> new SourceUnit(p, context));
    }

    /**
     * Gets the compilation context with the given uri, null if the compilation
     * root is not found for the uri.
     * Creating a new one if the compilation context does not already exist.
     *
     * @param uri The given uri for looking compilation root.
     * @return The compilation context.
     */
    public CompilationContext getCompilationContext(String uri) {
        for (CompilationContext context : contexts) {
            if (Utils.isSubPath(context.root, Utils.toPath(uri))) {
                return context;
            }
        }

        Path root = Utils.findScriptsUpwards(uri);
        if (root != null) {
            CompilationContext context = new CompilationContext(root);
            contexts.add(context);
            return context;
        }

        return null;
    }

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        Timer timer = new Timer();
        String uri = params.getTextDocument().getUri();
        SourceUnit sourceUnit = getSourceUnit(uri);
        server.logger.info("[didOpen] " + Utils.getFileName(uri) + ", took " + timer.cost());
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        Timer timer = new Timer();
        String uri = params.getTextDocument().getUri();
        getSourceUnit(uri).updateAll(params.getContentChanges().get(0).getText());
        server.logger.info("[didChange] " + Utils.getFileName(uri) + ", took " + timer.cost());
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        Timer timer = new Timer();
        String uri = params.getTextDocument().getUri();
        server.logger.info("[didClose] " + Utils.getFileName(uri) + ", took " + timer.cost());
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
        Timer timer = new Timer();
        String uri = params.getTextDocument().getUri();
        SourceUnit sourceUnit = getSourceUnit(uri);
        sourceUnit.updateAll(null);
        server.logger.info("[didSave] " + Utils.getFileName(uri) + ", took " + timer.cost());
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        Timer timer = new Timer();
        String uri = params.getTextDocument().getUri();
        SemanticTokens semanticTokens = SemanticTokensProvider.semanticTokensFull(getSourceUnit(uri), params);
        server.logger.info("[semanticTokensFull] " + Utils.getFileName(uri) + ", took " + timer.cost());
        return CompletableFuture.completedFuture(semanticTokens);
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        Timer timer = new Timer();
        String uri = params.getTextDocument().getUri();
        Hover hover = HoverProvider.hover(getSourceUnit(uri), params);
        server.logger.info("[hover] " + Utils.getFileName(uri) + ", took " + timer.cost());
        return CompletableFuture.completedFuture(hover);
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        Timer timer = new Timer();
        String uri = params.getTextDocument().getUri();
        List<LocationLink> definitions = DefinitionProvider.definition(getSourceUnit(uri), params);
        server.logger.info("[definition] " + Utils.getFileName(uri) + ", took " + timer.cost());
        return CompletableFuture.completedFuture(Either.forRight(definitions));
    }

    @SuppressWarnings("deprecation")
    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        Timer timer = new Timer();
        String uri = params.getTextDocument().getUri();
        List<DocumentSymbol> symbols = DocumentSymbolProvider.documentSymbol(getSourceUnit(uri), params);
        server.logger.info("[documentSymbol] " + Utils.getFileName(uri) + ", took " + timer.cost());
        return CompletableFuture.completedFuture(symbols.stream().map(Either::<SymbolInformation, DocumentSymbol>forRight).collect(Collectors.toList()));
    }

    /* End Text Document Service */

    /* Workspace Service */

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {
    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
        List<String> files = params.getChanges().stream()
                .map(event -> Utils.getFileName(event.getUri()))
                .collect(Collectors.toList());
        server.logger.info("[didChangeWatchedFiles] " + files);
    }

    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
        // TODO
        params.getEvent().getRemoved().forEach(workspace -> {
            server.logger.info("[didChangeWorkspaceFolders] Removed workspace: " + workspace);
        });
        params.getEvent().getAdded().forEach(workspace -> {
            server.logger.info("[didChangeWorkspaceFolders] Added workspace: " + workspace);
        });
    }

    /* End Workspace Service */

}
