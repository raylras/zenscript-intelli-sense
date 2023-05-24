package raylras.zen.langserver;

import org.antlr.v4.runtime.CharStreams;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.code.CompilationUnit;
import raylras.zen.langserver.provider.CompletionProvider;
import raylras.zen.langserver.provider.SemanticTokensProvider;
import raylras.zen.langserver.provider.SignatureProvider;
import raylras.zen.code.CompilationEnvironment;
import raylras.zen.util.Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ZenLanguageService implements TextDocumentService, WorkspaceService {

    public ZenLanguageServer server;
    public CompilationEnvironment env;

    public ZenLanguageService(ZenLanguageServer server) {
        this.server = server;
        this.env = new CompilationEnvironment();
    }

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        checkEnv(params.getTextDocument().getUri());
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        CompilationUnit unit = getCompilationUnit(params.getTextDocument().getUri());
        String source = params.getContentChanges().get(0).getText();
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
        CompilationUnit unit = getCompilationUnit(params.getTextDocument().getUri());
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
        CompilationUnit unit = getCompilationUnit(params.getTextDocument().getUri());
        CompletionList data = CompletionProvider.completion(unit, params);
        return CompletableFuture.completedFuture(Either.forRight(data));
    }

    @Override
    public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<SignatureHelp> signatureHelp(SignatureHelpParams params) {
        CompilationUnit unit = getCompilationUnit(params.getTextDocument().getUri());
        SignatureHelp data = SignatureProvider.signatureHelp(unit, params);
        return CompletableFuture.completedFuture(data);
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
                    loadCompilationUnit(unit);
                    env.addCompilationUnit(unit);
                    break;
                case Changed:
                    unit = env.getCompilationUnit(documentPath);
                    loadCompilationUnit(unit);
                    break;
                case Deleted:
                    env.removeCompilationUnit(documentPath);
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


    private void checkEnv(String uri) {
        Path documentPath = Utils.toPath(uri);
        if (env != null && Objects.equals(documentPath, env.scriptService.getRoot())) {
            return;
        }
        createCompilationContext(documentPath);
    }

    private void createCompilationContext(Path documentPath) {
        Path compilationRoot = Utils.findUpwards(documentPath, "scripts");
        if (compilationRoot == null)
            compilationRoot = documentPath;

        env.scriptService.setRoot(compilationRoot);
        loadCompilationUnits(compilationRoot);
    }

    private void loadCompilationUnits(Path compilationRoot) {
        try (Stream<Path> pathStream = Files.walk(compilationRoot)) {
            pathStream.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(CompilationUnit.FILE_EXTENSION))
                .forEach(unitPath -> {
                    CompilationUnit unit = new CompilationUnit(unitPath, env);
                    loadCompilationUnit(unit);
                    env.addCompilationUnit(unit);
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCompilationUnit(CompilationUnit unit) {
        try {
            unit.load(CharStreams.fromPath(unit.path, StandardCharsets.UTF_8));
            if (unit.isDzs()) {
                // TODO: redesign load method.
                env.libraryService.load(Collections.singletonList(unit));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCompilationUnit(CompilationUnit unit, String source) {
        unit.load(CharStreams.fromString(source, String.valueOf(unit.path)));
    }

    private CompilationUnit getCompilationUnit(String uri) {
        Path documentPath = Utils.toPath(uri);
        return env.getCompilationUnit(documentPath);
    }

}
