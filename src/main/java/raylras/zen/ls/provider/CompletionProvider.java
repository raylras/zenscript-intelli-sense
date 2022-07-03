package raylras.zen.ls.provider;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.CompileUnit;

import java.util.List;

// TODO: CompletionProvider
public class CompletionProvider {

    public Either<List<CompletionItem>, CompletionList> provideCompletion(@NotNull CompletionParams params, @NotNull CompileUnit compileUnit) {
        return null;
    }

}
