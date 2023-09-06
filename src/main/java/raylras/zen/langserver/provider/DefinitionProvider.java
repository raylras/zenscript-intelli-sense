package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.resolve.SymbolResolver;
import raylras.zen.code.symbol.ParseTreeLocatable;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.langserver.Document;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Position;
import raylras.zen.util.Range;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DefinitionProvider {

    private static final Logger logger = LoggerFactory.getLogger(DefinitionProvider.class);

    public static CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(Document doc, DefinitionParams params) {
        return doc.getUnit().map(unit -> CompletableFuture.supplyAsync(() -> {
            Position cursor = Position.of(params.getPosition());
            ParseTree cst = CSTNodes.getCstAtPosition(unit.getParseTree(), cursor);
            org.eclipse.lsp4j.Range originSelectionRange = Range.of(cst).toLspRange();
            Collection<Symbol> symbols = SymbolResolver.lookupSymbol(cst, unit);
            return Either.<List<? extends Location>, List<? extends LocationLink>>forRight(symbols.stream()
                    .filter(symbol -> symbol instanceof ParseTreeLocatable)
                    .map(symbol -> toLocationLink(symbol, originSelectionRange))
                    .toList());
        })).orElseGet(DefinitionProvider::empty);
    }

    public static CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> empty() {
        return CompletableFuture.completedFuture(null);
    }

    private static LocationLink toLocationLink(Symbol symbol, org.eclipse.lsp4j.Range originSelectionRange) {
        ParseTreeLocatable locatable = ((ParseTreeLocatable) symbol);
        String uri = locatable.getUri();
        org.eclipse.lsp4j.Range range = locatable.getRange().toLspRange();
        org.eclipse.lsp4j.Range selectionRange = locatable.getSelectionRange().toLspRange();
        return new LocationLink(uri, range, selectionRange, originSelectionRange);
    }

}

