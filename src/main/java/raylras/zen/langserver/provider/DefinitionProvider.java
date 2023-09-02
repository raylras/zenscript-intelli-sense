package raylras.zen.langserver.provider;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.LocationLink;
import raylras.zen.code.CompilationUnit;
import raylras.zen.code.resolve.SymbolResolver;
import raylras.zen.code.symbol.Locatable;
import raylras.zen.code.symbol.Symbol;
import raylras.zen.util.CSTNodes;
import raylras.zen.util.Range;
import raylras.zen.util.Ranges;

import java.util.List;

public class DefinitionProvider {

    public static List<LocationLink> definition(CompilationUnit unit, DefinitionParams params) {
        Range cursor = Ranges.of(params.getPosition());
        ParseTree cst = CSTNodes.getCstAtLineAndColumn(unit.getParseTree(), cursor.startLine, cursor.startColumn);
        List<Symbol> symbols = SymbolResolver.getSymbol(cst, unit);
        return symbols.stream()
                .filter(symbol -> symbol instanceof Locatable)
                .map(symbol -> {
                    Locatable locatable = ((Locatable) symbol);
                    String uri = locatable.getUri();
                    org.eclipse.lsp4j.Range range = locatable.getRange().toLspRange();
                    org.eclipse.lsp4j.Range selectionRange = locatable.getSelectionRange().toLspRange();
                    org.eclipse.lsp4j.Range originSelectionRange = Ranges.toLSPRange(cst);
                    return new LocationLink(uri, range, selectionRange, originSelectionRange);
                })
                .toList();
    }

}

