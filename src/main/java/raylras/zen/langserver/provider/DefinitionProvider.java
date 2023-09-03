package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.LocationLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.SymbolResolver;
import raylras.zen.code.symbol.Locatable;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.code.symbol.UriLocatable;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Position;
import raylras.zen.util.Range;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DefinitionProvider {

    private static final Logger logger = LoggerFactory.getLogger(DefinitionProvider.class);
    public static List<LocationLink> definition(CompilationUnit unit, DefinitionParams params) {
        Position cursor = Position.of(params.getPosition());
        ParseTree cst = CSTNodes.getCstAtPosition(unit.getParseTree(), cursor);
        if(cst == null) {
            logger.warn("Could not get symbol at ({}, {}), skipping goto definition", params.getPosition().getLine(), params.getPosition().getCharacter());
            return Collections.emptyList();
        }
        org.eclipse.lsp4j.Range originSelectionRange = Range.of(cst).toLspRange();
        Collection<Symbol> symbols = SymbolResolver.lookupSymbol(cst, unit);
        return symbols.stream()
                .filter(symbol -> symbol instanceof UriLocatable)
                .map(symbol -> toLocationLink(symbol, originSelectionRange))
                .toList();
    }

    private static LocationLink toLocationLink(Symbol symbol, org.eclipse.lsp4j.Range originSelectionRange) {
        UriLocatable uriLocatable = ((UriLocatable) symbol);
        String uri = uriLocatable.getUri();
        if(uriLocatable instanceof Locatable locatable) {
            org.eclipse.lsp4j.Range range = locatable.getRange().toLspRange();
            org.eclipse.lsp4j.Range selectionRange = locatable.getSelectionRange().toLspRange();
            return new LocationLink(uri, range, selectionRange, originSelectionRange);
        }
        org.eclipse.lsp4j.Position empty = new org.eclipse.lsp4j.Position();
        org.eclipse.lsp4j.Range range = new org.eclipse.lsp4j.Range(empty, empty);
        return new LocationLink(uri, range, range, originSelectionRange);
    }

}

