package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.ScriptNode;

import java.util.Collections;
import java.util.List;

// TODO: CompletionProvider
public class CompletionProvider {

    public Either<List<CompletionItem>, CompletionList> provideCompletion(CompletionParams params, @NotNull ScriptNode scriptNode) {
        return Either.forLeft(Collections.emptyList());
    }

}
