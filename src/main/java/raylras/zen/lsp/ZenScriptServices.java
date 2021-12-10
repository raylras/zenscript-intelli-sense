package raylras.zen.lsp;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.util.CommonErrorLogger;
import stanhebben.zenscript.ZenModule;
import stanhebben.zenscript.ZenParsedFile;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.impl.GenericCompileEnvironment;
import stanhebben.zenscript.impl.GenericFunctions;
import stanhebben.zenscript.impl.GenericRegistry;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ZenScriptServices implements TextDocumentService, WorkspaceService {
    
    private Path workspacePath;
    private Path scriptsPath;

    GenericCompileEnvironment compileEnvironment;
    GenericRegistry registry;
    IEnvironmentGlobal environmentGlobal;

    public ZenScriptServices() {
        compileEnvironment = new GenericCompileEnvironment();
        registry = new GenericRegistry(compileEnvironment, new CommonErrorLogger());
        registry.registerGlobal("print", registry.getStaticFunction(GenericFunctions.class, "print", String.class));
        Map<String, byte[]> classes = new HashMap<>();
        environmentGlobal = registry.makeGlobalEnvironment(classes);
    }

    public Path getWorkspacePath() {
        return workspacePath;
    }

    public Path getScriptsPath() {
        return scriptsPath;
    }

    public void setWorkspacePath(Path workspacePath) {
        this.workspacePath = workspacePath;
        Manager.getClient().logMessage(new MessageParams(MessageType.Info,"Workspace: " + workspacePath.toString()));
        Manager.getClient().logMessage(new MessageParams(MessageType.Info,"Scripts root: " + scriptsPath));
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams params) {
        Path path = Paths.get(URI.create(params.getTextDocument().getUri()));
        String pathStr = path.toString();

        // If the opened file path is similar to "D:\foo\scripts\bar\baz.zs", then set scriptsPath to "D:\foo\scripts"
        if (scriptsPath == null) {
            scriptsPath = Paths.get(pathStr.substring(0, pathStr.indexOf("scripts") + "scripts".length()));
        }
        Path relativePath = scriptsPath.relativize(path); // such as "bar\baz.zs"

        Manager.getClient().logMessage(new MessageParams(MessageType.Info,"\n"));
        Manager.getClient().logMessage(new MessageParams(MessageType.Info,"Opened file: " + path));
        Manager.getClient().logMessage(new MessageParams(MessageType.Info,"File name: " + path.getFileName()));
        Manager.getClient().logMessage(new MessageParams(MessageType.Info,"Scripts root: " + scriptsPath));
        Manager.getClient().logMessage(new MessageParams(MessageType.Info,"Relative path: " + relativePath));

        String fileName = path.toFile().getName();
        String className = ZenModule.extractClassName(relativePath.toString());

        ZenTokener tokener;
        ZenParsedFile parsedFile;
        try {
            tokener = new ZenTokener(params.getTextDocument().getText(), compileEnvironment, fileName, false);
            parsedFile = new ZenParsedFile(fileName, className, tokener, environmentGlobal);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {

    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {

    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {

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
        return null;
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
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
    
}
