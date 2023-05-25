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
import raylras.zen.service.FileManager;
import raylras.zen.util.Logger;
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
    private static final raylras.zen.util.Logger logger = Logger.getLogger("main");
    public ZenLanguageServer server;

    public final FileManager fileManager;

    public ZenLanguageService(ZenLanguageServer server) {
        this.server = server;
        this.fileManager = new FileManager();
    }

    /* Text Document Service */

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        fileManager.open(params);
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        fileManager.change(params);
    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        fileManager.close(params);
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
        fileManager.beginUpdate();
        params.getChanges().forEach(event -> {
            Path documentPath = Utils.toPath(event.getUri());
            switch (event.getType()) {
                case Created:
                    fileManager.externalCreate(documentPath);
                    break;
                case Changed:
                    fileManager.externalChange(documentPath);
                    break;
                case Deleted:
                    fileManager.externalDelete(documentPath);
                    break;
            }
        });

        fileManager.finishUpdate();
    }

    @Override
    public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
        fileManager.beginUpdate();
        params.getEvent().getRemoved().forEach(fileManager::deleteWorkspace);
        params.getEvent().getAdded().forEach(fileManager::addWorkspace);
        fileManager.finishUpdate();
    }

    /* End Workspace Service */

    private CompilationUnit getCompilationUnit(String uri) {
        Path documentPath = Utils.toPath(uri);
        return fileManager.getCompilationUnit(documentPath);
    }



}
