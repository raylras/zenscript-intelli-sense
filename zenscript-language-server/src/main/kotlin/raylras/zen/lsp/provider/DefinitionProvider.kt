package raylras.zen.lsp.provider

import org.eclipse.lsp4j.DefinitionParams
import org.eclipse.lsp4j.LocationLink
import org.eclipse.lsp4j.Range
import raylras.zen.model.CompilationUnit
import raylras.zen.model.resolve.lookupSymbol
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.Symbol
import raylras.zen.util.getCstAtPosition
import raylras.zen.util.textRange
import raylras.zen.util.toLspRange
import raylras.zen.util.toTextPosition

object DefinitionProvider {
    fun definition(unit: CompilationUnit?, params: DefinitionParams): List<LocationLink>? {
        unit ?: return null
        val cursor = params.position.toTextPosition()
        val cst = getCstAtPosition(unit.parseTree, cursor) ?: return null
        val originSelectionRange = cst.textRange.toLspRange()
        return lookupSymbol(cst, unit)
            .filter { symbol: Symbol? -> symbol is ParseTreeLocatable }
            .map { symbol: Symbol -> toLocationLink(symbol, originSelectionRange) }
            .toList()
    }

    private fun toLocationLink(symbol: Symbol, originSelectionRange: Range): LocationLink {
        val locatable = (symbol as ParseTreeLocatable)
        val uri = locatable.path.toUri().toString()
        val range = locatable.textRange.toLspRange()
        val selectionRange = locatable.selectionTextRange.toLspRange()
        return LocationLink(uri, range, selectionRange, originSelectionRange)
    }
}
