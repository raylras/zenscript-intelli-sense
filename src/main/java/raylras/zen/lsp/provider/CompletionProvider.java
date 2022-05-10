package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// TODO: CompletionProvider
public class CompletionProvider {

    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> provideCompletion(String uri, Position position) {
        return null;
    }

}
