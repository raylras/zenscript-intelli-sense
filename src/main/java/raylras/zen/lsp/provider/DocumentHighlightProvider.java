package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.DocumentHighlight;
import raylras.zen.ast.ScriptNode;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// TODO: DocumentHighlightProvider
public class DocumentHighlightProvider {

    public CompletableFuture<List<DocumentHighlight>> provideDocumentHighlight(ScriptNode scriptUnit) {
        return null;
    }

}
