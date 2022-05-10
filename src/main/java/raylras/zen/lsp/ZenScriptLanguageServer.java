package raylras.zen.lsp;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.antlr.ASTBuilder;
import raylras.zen.antlr.ZenScriptLexer;
import raylras.zen.antlr.ZenScriptParser;
import raylras.zen.ast.ScriptNode;
import raylras.zen.verify.Environment;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ZenScriptLanguageServer implements LanguageServer {

    private final ZenScriptServices services;

    public ZenScriptLanguageServer(Environment env) {
        this.services = new ZenScriptServices(env);
    }

    public ZenScriptServices getServices() {
        return services;
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
        initScripts(params);

        ServerCapabilities capabilities = new ServerCapabilities();
//        capabilities.setCompletionProvider(new CompletionOptions());
//        capabilities.setTextDocumentSync(TextDocumentSyncKind.Full);
//        capabilities.setDocumentSymbolProvider(true);
//        capabilities.setWorkspaceSymbolProvider(true);
//        capabilities.setDocumentHighlightProvider(true);
//        SignatureHelpOptions signatureHelpOptions = new SignatureHelpOptions();
//        signatureHelpOptions.setTriggerCharacters(Arrays.asList("(", ","));
//        capabilities.setSignatureHelpProvider(signatureHelpOptions);
        SemanticTokensWithRegistrationOptions semanticTokensOptions = new SemanticTokensWithRegistrationOptions();
        semanticTokensOptions.setLegend(new SemanticTokensLegend(TokenType.getTokenTypes(), TokenModifier.getTokenTypeModifiers()));
        semanticTokensOptions.setFull(true);
        capabilities.setSemanticTokensProvider(semanticTokensOptions);
//        capabilities.setReferencesProvider(true);
//        capabilities.setDefinitionProvider(true);
//        capabilities.setTypeDefinitionProvider(true);
//        capabilities.setHoverProvider(true);
//        capabilities.setRenameProvider(true);

        return CompletableFuture.completedFuture(new InitializeResult(capabilities));
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        return null;
    }

    @Override
    public void exit() {}

    @Override
    public TextDocumentService getTextDocumentService() {
        return services;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return services;
    }

    private void initScripts(InitializeParams params) {

        // Get the workspace, if there are more than one, only process the first one
        params.getWorkspaceFolders().stream().findFirst().ifPresent(workspaceFolder -> {
            services.setWorkspaceFolder(workspaceFolder);
            CompletableFuture.runAsync(() -> {
                Path workspace = Paths.get(URI.create(workspaceFolder.getUri()));

                // Recursively find a folder named "scripts" under the workspace
                try(Stream<Path> pathStream = Files.find(workspace, 10, (path, attributes) -> attributes.isDirectory() && "scripts".equalsIgnoreCase(path.getFileName().toString()))) {

                    // There may be multiple "scripts", only the first one is processed
                    pathStream.findFirst().ifPresent(scripts -> services.setScriptsFolder(scripts.toUri()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).thenApply(unused -> {

                if (services.getScriptsFolder() != null) {

                    // Recursively find all ".zs" files under "scripts"
                    Path scripts = Paths.get(services.getScriptsFolder());
                    try(Stream<Path> pathStream = Files.find(scripts, 10, (path, attributes) -> path.getFileName().toString().matches(".*\\.zs"))) {
                        return pathStream.collect(Collectors.toList());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // The "scripts" folder may not be found in the workspace, returning an empty list
                return Collections.<Path>emptyList();

            }).thenAccept(files -> {
                for (Path file : files) {
                    System.out.println(services.getScriptsFolder().relativize(file.toUri()));
                    try {
                        CharStream charStream = CharStreams.fromPath(file);
                        ZenScriptLexer lexer = new ZenScriptLexer(charStream);
                        CommonTokenStream tokens = new CommonTokenStream(lexer);
                        ZenScriptParser parser = new ZenScriptParser(tokens);
                        ZenScriptParser.ScriptUnitContext scriptContext = parser.scriptUnit();
                        ASTBuilder cstVisitor = new ASTBuilder();
                        ScriptNode script = cstVisitor.visitScriptUnit(scriptContext);
                        services.visitor.getAstNodeListByURI().put(file.toUri(), cstVisitor.getAstNodeList());
                        services.visitor.visitScriptNode(script);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

}
