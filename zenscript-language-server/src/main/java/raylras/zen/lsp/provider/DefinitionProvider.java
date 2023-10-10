package raylras.zen.lsp.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import raylras.zen.model.CompilationUnit;
import raylras.zen.model.resolve.SymbolResolver;
import raylras.zen.model.symbol.ParseTreeLocatable;
import raylras.zen.model.symbol.Symbol;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Position;
import raylras.zen.util.Range;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DefinitionProvider {

    public static Optional<Either<List<? extends Location>, List<? extends LocationLink>>> definition(CompilationUnit unit, DefinitionParams params) {
        Position cursor = Position.of(params.getPosition());
        ParseTree cst = CSTNodes.getCstAtPosition(unit.getParseTree(), cursor);
        org.eclipse.lsp4j.Range originSelectionRange = Range.of(cst).toLspRange();
        List<LocationLink> list = SymbolResolver.lookupSymbol(cst, unit).stream()
                .filter(symbol -> symbol instanceof ParseTreeLocatable)
                .map(symbol -> toLocationLink(symbol, originSelectionRange))
                .toList();
        if (list.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(Either.forRight(list));
        }
    }

    public static CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> empty() {
        return CompletableFuture.completedFuture(null);
    }

    private static LocationLink toLocationLink(Symbol symbol, org.eclipse.lsp4j.Range originSelectionRange) {
        ParseTreeLocatable locatable = ((ParseTreeLocatable) symbol);
        String uri = locatable.getPath().toUri().toString();
        org.eclipse.lsp4j.Range range = locatable.getRange().toLspRange();
        org.eclipse.lsp4j.Range selectionRange = locatable.getSelectionRange().toLspRange();
        return new LocationLink(uri, range, selectionRange, originSelectionRange);
    }

}

