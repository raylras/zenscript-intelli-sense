package raylras.zen.lsp;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.antlr.ASTBuilder;
import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.ast.ScriptNode;
import raylras.zen.lsp.provider.SemanticTokensFullProvider;
import raylras.zen.verify.Environment;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ZenScriptServices implements TextDocumentService, WorkspaceService {

    private ZenScriptLanguageServer server;
    private LanguageClient client;
    private WorkspaceFolder workspaceFolder;
    private URI scriptsFolder;

    public final ZenScriptVisitor visitor;
    public final SemanticTokensFullProvider semanticProvider;

    public ZenScriptServices(Environment env) {
        this.visitor = new ZenScriptVisitor(env);
        this.semanticProvider = new SemanticTokensFullProvider(visitor);
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        String uri = params.getTextDocument().getUri();

        info("\n");
        info("Opened file: " + uri);
        info("Version: " + params.getTextDocument().getVersion());
        info("Workspace Folder: " + workspaceFolder.getUri());

        // ANTLR
        CharStream charStream = CharStreams.fromString(params.getTextDocument().getText(), uri);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);
        ZenScriptParser.ScriptUnitContext ScriptUnitContext = parser.scriptUnit();


    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {
        String uri = params.getTextDocument().getUri();
        Path path = Paths.get(URI.create(uri));
        String fileName = path.toFile().getName();

        CharStream charStream = CharStreams.fromString(params.getContentChanges().get(0).getText(), fileName);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);
        ZenScriptParser.ScriptUnitContext ScriptUnitContext = parser.scriptUnit();

        // ScriptUnitContextMap.put(uri, ScriptUnitContext);
        client.refreshSemanticTokens();

    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {

    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
        String uri = params.getTextDocument().getUri();
        Path path = Paths.get(URI.create(uri));
        String fileName = path.toFile().getName();

    }

    @Override
    public void didChangeConfiguration(DidChangeConfigurationParams params) {

    }

    @Override
    public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {

    }

    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
        String uri = params.getTextDocument().getUri();
        Position position = params.getPosition();
//        return new CompletionProvider().provideCompletion(uri, position);
        return null;
    }


    @Override
    public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
        return null;
    }

    @Override
    public CompletableFuture<Hover> hover(HoverParams params) {
        return null;
    }

    @Override
    public CompletableFuture<SignatureHelp> signatureHelp(SignatureHelpParams params) {
        String uri = params.getTextDocument().getUri();
//        return new SignatureHelpProvider(uri, parsedFiles.get(uri)).provideSignatureHelp(params);
        return null;
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> declaration(DeclarationParams params) {
        return null;
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(DefinitionParams params) {
        return null;
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> typeDefinition(TypeDefinitionParams params) {
        return null;
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> implementation(ImplementationParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(DocumentHighlightParams params) {
//        String uri = params.getTextDocument().getUri();
//        Position pos = params.getPosition();
//        ZenTokener t = parsedFiles.get(uri).getTokener();
//        t.getToken(pos.getLine() + 1, pos.getCharacter() + 1);
//
//        return new DocumentHighlightProvider(uri, parsedFiles.get(uri)).provideDocumentHighlight(params);
        return null;
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        String uri = params.getTextDocument().getUri();
//        return new DocumentSymbolProvider(ScriptUnitContextMap.get(uri)).provideDocumentSymbol(params);
        return null;
    }

    @Override
    public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
        return null;
    }

    @Override
    public CompletableFuture<CodeAction> resolveCodeAction(CodeAction unresolved) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
        return null;
    }

    @Override
    public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
        return null;
    }

    @Override
    public CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
        return null;
    }

    @Override
    public CompletableFuture<SemanticTokens> semanticTokensFull(SemanticTokensParams params) {
        return semanticProvider.provideSemanticTokensFull(params);
    }

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

    public WorkspaceFolder getWorkspaceFolder() {
        return workspaceFolder;
    }

    public void setWorkspaceFolder(WorkspaceFolder workspaceFolder) {
        this.workspaceFolder = workspaceFolder;
    }

    public URI getScriptsFolder() {
        return scriptsFolder;
    }

    public void setScriptsFolder(URI scriptsFolder) {
        this.scriptsFolder = scriptsFolder;
    }

    public Environment getEnv() {
        return visitor.getEnv();
    }

    // utils

    public void info(String message) {
        client.logMessage(new MessageParams(MessageType.Info, message));
    }


    public void compileScriptUnit(URI uri, String text) {
        CharStream charStream = CharStreams.fromString(text);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);

        ScriptNode scriptNode = new ASTBuilder().visitScriptUnit(parser.scriptUnit(), uri);


    }

}
