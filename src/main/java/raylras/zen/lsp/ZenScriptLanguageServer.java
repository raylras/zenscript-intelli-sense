package raylras.zen.lsp;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import raylras.zen.ast.CompileUnit;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

public class ZenScriptLanguageServer implements LanguageServer {

    private final ZenScriptServices services;

    public ZenScriptLanguageServer() {
        this.services = new ZenScriptServices();
    }

    public ZenScriptServices getServices() {
        return services;
    }

    private void initScripts(InitializeParams params) {
        WorkspaceFolder workspace = params.getWorkspaceFolders().get(0);
        services.setWorkspace(workspace);
        Path workspacePath = Paths.get(URI.create(workspace.getUri()));

        // find "scripts" dir using BFS
        Path scripts = null;
        Queue<File> queue = new ArrayDeque<>();
        queue.add(workspacePath.toFile());
        while (!queue.isEmpty()) {
            File current = queue.poll();
            if ("scripts".equalsIgnoreCase(current.getName())) {
                scripts = current.toPath();
                break;
            }
            File[] listFiles = current.listFiles();
            if (listFiles == null) continue;
            for (File dir : listFiles) {
                if (dir.isDirectory()) {
                    queue.add(dir);
                }
            }
        }

        if (scripts != null) {
            try {
                services.setCompileUnit(CompileUnit.fromPath(scripts));
            } catch (Throwable t) {
                services.info(t.getMessage());
            }
        } else {
            services.info("Could not find \"scripts\" folder under workspace " + workspace);
        }
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
        initScripts(params);

        ServerCapabilities capabilities = new ServerCapabilities();
//        capabilities.setCompletionProvider(new CompletionOptions());
        capabilities.setTextDocumentSync(TextDocumentSyncKind.Full);
        capabilities.setDocumentSymbolProvider(true);
//        capabilities.setWorkspaceSymbolProvider(true);
//        capabilities.setDocumentHighlightProvider(true);
//        SignatureHelpOptions signatureHelpOptions = new SignatureHelpOptions();
//        signatureHelpOptions.setTriggerCharacters(Arrays.asList("(", ","));
//        capabilities.setSignatureHelpProvider(signatureHelpOptions);
        capabilities.setSemanticTokensProvider(new SemanticTokensWithRegistrationOptions(new SemanticTokensLegend(TokenType.getTokenTypes(), TokenModifier.getTokenModifiers()), true));
//        capabilities.setReferencesProvider(true);
//        capabilities.setDefinitionProvider(true);
//        capabilities.setTypeDefinitionProvider(true);
        capabilities.setHoverProvider(true);
//        capabilities.setRenameProvider(true);

        return CompletableFuture.completedFuture(new InitializeResult(capabilities));
    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return services;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return services;
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        return null;
    }

    @Override
    public void exit() {
        System.exit(0);
    }

}
