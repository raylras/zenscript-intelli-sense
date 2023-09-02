package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.LocationLink;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.SymbolResolver;
import raylras.zen.code.symbol.Locatable;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Position;
import raylras.zen.util.Range;

import java.util.Collection;
import java.util.List;

public class DefinitionProvider {

    public static List<LocationLink> definition(CompilationUnit unit, DefinitionParams params) {
        Position cursor = Position.of(params.getPosition());
        ParseTree cst = CSTNodes.getCstAtPosition(unit.getParseTree(), cursor);
        org.eclipse.lsp4j.Range originSelectionRange = Range.of(cst).toLspRange();
        Collection<Symbol> symbols = SymbolResolver.lookupSymbol(cst, unit);
        return symbols.stream()
                .filter(symbol -> symbol instanceof Locatable)
                .map(symbol -> toLocationLink(symbol, originSelectionRange))
                .toList();
    }

    private static LocationLink toLocationLink(Symbol symbol, org.eclipse.lsp4j.Range originSelectionRange) {
        Locatable locatable = ((Locatable) symbol);
        String uri = locatable.getUri();
        org.eclipse.lsp4j.Range range = locatable.getRange().toLspRange();
        org.eclipse.lsp4j.Range selectionRange = locatable.getSelectionRange().toLspRange();
        return new LocationLink(uri, range, selectionRange, originSelectionRange);
    }

}

