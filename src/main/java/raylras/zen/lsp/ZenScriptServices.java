package raylras.zen.lsp;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.lsp.antlr.ZenScriptLexer;
import raylras.zen.lsp.antlr.ZenScriptParser;
import raylras.zen.lsp.provider.DocumentSymbolProvider;
import raylras.zen.lsp.provider.SemanticTokensFullProvider;
import raylras.zen.scope.CommonScope;
import raylras.zen.scope.Scope;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ZenScriptServices implements TextDocumentService, WorkspaceService {

    private Path workspacePath;
    private Path scriptsPath;

    List<Diagnostic> diagnostics;
//    GenericCompileEnvironment compileEnvironment;
//    GenericRegistry registry;
//    IEnvironmentGlobal environmentGlobal;
    Map<String, ZenScriptParser.ScriptContext> scriptContextMap;

    public ZenScriptServices() {
        diagnostics = new LinkedList<>();
//        compileEnvironment = new GenericCompileEnvironment();
//        registry = new GenericRegistry(compileEnvironment, new CommonErrorHandler(diagnostics));
//        environmentGlobal = registry.makeGlobalEnvironment(new HashMap<>());
        scriptContextMap = new HashMap<>();
    }

    public Path getWorkspacePath() {
        return workspacePath;
    }

    public Path getScriptsPath() {
        return scriptsPath;
    }

    public void setWorkspacePath(Path workspacePath) {
        this.workspacePath = workspacePath;
        Manager.getClient().logMessage(new MessageParams(MessageType.Info, "Workspace: " + workspacePath.toString()));
        Manager.getClient().logMessage(new MessageParams(MessageType.Info, "Scripts root: " + scriptsPath));
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        String uri = params.getTextDocument().getUri();
        Path path = Paths.get(URI.create(uri));
        String pathStr = path.toString();
        String fileName = path.toFile().getName();
//        Path relativePath = scriptsPath.relativize(path); // such as "bar\baz.zs"
//        String className = ZenModule.extractClassName(relativePath.toString());

        // If the opened file path is similar to "D:\foo\scripts\bar\baz.zs", then set scriptsPath to "D:\foo\scripts"
        scriptsPath = Paths.get(pathStr.substring(0, pathStr.indexOf("scripts") + "scripts".length()));

        Manager.getClient().logMessage(new MessageParams(MessageType.Info, "\n"));
        Manager.getClient().logMessage(new MessageParams(MessageType.Info, "Opened file: " + path));
        Manager.getClient().logMessage(new MessageParams(MessageType.Info, "File name: " + path.getFileName()));
        Manager.getClient().logMessage(new MessageParams(MessageType.Info, "Scripts root: " + scriptsPath));
//        Manager.getClient().logMessage(new MessageParams(MessageType.Info, "Relative path: " + relativePath));

        // ANTLR
        CharStream charStream = CharStreams.fromString(params.getTextDocument().getText(), fileName);
        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ZenScriptParser parser = new ZenScriptParser(tokens);
        ZenScriptParser.ScriptContext scriptContext = parser.script();

        scriptContextMap.put(uri, scriptContext);

        Scope global = new CommonScope(null, "Global");

        //ZenScriptDefinitionParser defParser = new ZenScriptDefinitionParser(fileName, global);
        //defParser.visit(scriptContext);

//        PublishDiagnosticsParams diagnosticsParams = new PublishDiagnosticsParams(uri, diagnostics);
//        Manager.getClient().publishDiagnostics(diagnosticsParams);
//        diagnostics.clear();

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
        ZenScriptParser.ScriptContext scriptContext = parser.script();

        scriptContextMap.put(uri, scriptContext);
        Manager.getClient().refreshSemanticTokens();

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
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams position) {
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
        return new DocumentSymbolProvider(scriptContextMap.get(uri)).provideDocumentSymbol(params);
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
        String uri = params.getTextDocument().getUri();
        return new SemanticTokensFullProvider(scriptContextMap.get(uri)).provideSemanticTokensFull(params);
    }

}
