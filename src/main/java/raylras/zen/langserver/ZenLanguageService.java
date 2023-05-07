package raylras.zen.langserver;

import org.antlr.v4.runtime.CharStreams;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.code.CompilationContext;
import raylras.zen.code.CompilationUnit;
import raylras.zen.langserver.provider.CompletionProvider;
import raylras.zen.langserver.provider.SemanticTokensProvider;
import raylras.zen.util.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ZenLanguageService implements TextDocumentService, WorkspaceService {

    public ZenLanguageServer server;
    public CompilationContext context;

    public ZenLanguageService(ZenLanguageServer server) {
        this.server = server;
    }

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        Path documentPath = Utils.toPath(params.getTextDocument().getUri());
        checkContext(documentPath);
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        Path documentPath = Utils.toPath(params.getTextDocument().getUri());
        String source = params.getContentChanges().get(0).getText();
        CompilationUnit unit = context.getCompilationUnit(documentPath);
        loadCompilationUnit(unit, source);
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        SemanticTokens data = SemanticTokensProvider.semanticTokensFull(context, params);
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
        List<CompletionItem> data = CompletionProvider.completion(context, params);
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
                    unit = new CompilationUnit(documentPath, context);
                    loadCompilationUnit(unit);
                    context.addCompilationUnit(unit);
                    break;
                case Changed:
                    unit = context.getCompilationUnit(documentPath);
                    loadCompilationUnit(unit);
                    break;
                case Deleted:
                    context.removeCompilationUnit(documentPath);
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


    private void checkContext(Path documentPath) {
        if (context != null && Objects.equals(documentPath, context.compilationRoot)) {
            return;
        }
        createCompilationContext(documentPath);
    }

    private void createCompilationContext(Path documentPath) {
        Path compilationRoot = Utils.findUpwards(documentPath, "scripts");
        if (compilationRoot == null)
            compilationRoot = documentPath;
        context = new CompilationContext(compilationRoot);
        loadCompilationUnits(context);
    }

    private void loadCompilationUnits(CompilationContext context) {
        try (Stream<Path> pathStream = Files.walk(context.compilationRoot)) {
            pathStream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(CompilationUnit.FILE_EXTENSION))
                    .forEach(unitPath -> {
                        CompilationUnit unit = new CompilationUnit(unitPath, context);
                        loadCompilationUnit(unit);
                        context.addCompilationUnit(unit);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCompilationUnit(CompilationUnit unit) {
        try {
            unit.load(CharStreams.fromPath(unit.path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCompilationUnit(CompilationUnit unit, String source) {
        unit.load(CharStreams.fromString(source, String.valueOf(unit.path)));
    }

}
