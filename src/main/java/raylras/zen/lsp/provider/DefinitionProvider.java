package raylras.zen.lsp.provider;

import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.CompileUnit;

import java.util.Collections;
import java.util.List;

public class DefinitionProvider {

    @NotNull
    public Either<List<? extends Location>, List<? extends LocationLink>> provideDefinition(DefinitionParams params, @NotNull CompileUnit compileUnit) {
        return Either.forLeft(Collections.emptyList());
    }

}
