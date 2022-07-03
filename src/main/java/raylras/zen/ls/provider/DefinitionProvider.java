package raylras.zen.ls.provider;

import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.CompileUnit;

import java.util.List;

// TODO: DefinitionProvider
public class DefinitionProvider {

    public Either<List<? extends Location>, List<? extends LocationLink>> provideDefinition(@NotNull DefinitionParams params, @NotNull CompileUnit compileUnit) {
        return null;
    }

}
