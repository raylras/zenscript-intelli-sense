package raylras.zen.lsp;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.services.*;

import java.net.URI;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class ZenScriptLanguageServer implements LanguageServer {

    private final ZenScriptServices services;

    public ZenScriptLanguageServer() {
        this.services = new ZenScriptServices();
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
        params.getWorkspaceFolders().stream().findFirst().ifPresent(workspaceFolder -> services.setWorkspacePath(Paths.get(URI.create(workspaceFolder.getUri()))));

        ServerCapabilities capabilities = new ServerCapabilities();
        capabilities.setCompletionProvider(new CompletionOptions());
        capabilities.setTextDocumentSync(TextDocumentSyncKind.Full);
//        capabilities.setDocumentSymbolProvider(true);
//        capabilities.setWorkspaceSymbolProvider(true);
//        capabilities.setDocumentSymbolProvider(true);
//        capabilities.setReferencesProvider(true);
//        capabilities.setDefinitionProvider(true);
//        capabilities.setTypeDefinitionProvider(true);
//        capabilities.setHoverProvider(true);
//        capabilities.setRenameProvider(true);

        return CompletableFuture.completedFuture(new InitializeResult(capabilities));
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        return CompletableFuture.completedFuture(new Object());
    }

    @Override
    public void exit() {

    }

    public ZenScriptServices getServices() {
        return services;
    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return services;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return services;
    }

}
