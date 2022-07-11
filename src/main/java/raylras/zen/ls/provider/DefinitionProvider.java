package raylras.zen.ls.provider;

import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.jetbrains.annotations.NotNull;
import raylras.zen.ast.*;

import java.util.Collections;
import java.util.List;

// TODO: DefinitionProvider
public class DefinitionProvider {

    public Either<List<? extends Location>, List<? extends LocationLink>> provideDefinition(@NotNull DefinitionParams params, @NotNull CompileUnit compileUnit) {
        ScriptNode scriptNode = compileUnit.getScriptNode(params.getTextDocument().getUri());
        List<Node> nodeAtPosition = scriptNode.getNodeAtPosition(Position.of(params.getPosition()));
        return nodeAtPosition.stream()
                .filter(node -> node instanceof HasSymbol)
                .map(node -> ((HasSymbol) node).getSymbol())
                .findFirst()
                .map(symbol -> {
                    String uri = symbol.uri().toString();
                    Range targetRange = symbol.getRange();
                    Range selectionRange = symbol.getIdRange();
                    return new LocationLink(uri, targetRange.toLSPRange(), selectionRange.toLSPRange());
                })
                .map(location -> Either.<List<? extends Location>, List<? extends LocationLink>>forRight(Collections.singletonList(location)))
                .orElse(null);
    }

}
