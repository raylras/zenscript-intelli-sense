package raylras.zen.lsp.provider

import org.eclipse.lsp4j.DefinitionParams
import org.eclipse.lsp4j.LocationLink
import org.eclipse.lsp4j.Range
import raylras.zen.model.CompilationUnit
import raylras.zen.model.resolve.resolveSymbols
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.Symbol
import raylras.zen.util.getTerminalAt
import raylras.zen.util.textRange
import raylras.zen.util.toLspRange
import raylras.zen.util.toTextPosition

object DefinitionProvider {
    fun definition(unit: CompilationUnit?, params: DefinitionParams): List<LocationLink>? {
        unit ?: return null
        val cursor = params.position.toTextPosition()
        val terminal = unit.parseTree.getTerminalAt(cursor) ?: return null
        val originSelectionRange = terminal.textRange.toLspRange()
        return resolveSymbols<Symbol>(terminal, unit)
            .filter { it is ParseTreeLocatable }
            .map { toLocationLink(it, originSelectionRange) }
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
