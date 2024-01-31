package raylras.zen.lsp.provider

import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import org.eclipse.lsp4j.DefinitionParams
import org.eclipse.lsp4j.LocationLink
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveSymbols
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.Symbol
import raylras.zen.util.*

object DefinitionProvider {
    fun definition(unit: CompilationUnit, params: DefinitionParams): List<LocationLink> {
        val cursor = params.position.toTextPosition()
        val terminal = unit.parseTree.getTerminalAt(cursor) ?: return emptyList()
        val visitor = DefinitionVisitor(unit, terminal)
        unit.accept(visitor)
        return visitor.result.toList()
    }
}

private class DefinitionVisitor(private val unit: CompilationUnit, private val terminal: TerminalNode) : Visitor<Unit>() {
    var result = emptySequence<LocationLink>()

    override fun visitQualifiedName(ctx: QualifiedNameContext) {
        result = resolveSymbols<Symbol>(ctx, unit)
            .filterIsInstance<ParseTreeLocatable>()
            .map { it.toLocationLink(ctx.textRange) }
    }

    override fun visitSimpleNameExpr(ctx: SimpleNameExprContext) {
        result = resolveSymbols<Symbol>(ctx, unit)
            .filterIsInstance<ParseTreeLocatable>()
            .map { it.toLocationLink(ctx.textRange) }
    }

    override fun visitMemberAccessExpr(ctx: MemberAccessExprContext) {
        when (terminal) {
            in ctx.simpleName() -> {
                result = resolveSymbols<Symbol>(ctx, unit)
                    .filterIsInstance<ParseTreeLocatable>()
                    .map { it.toLocationLink(ctx.simpleName().textRange) }
            }

            else -> {
                visitChildren(ctx)
            }
        }
    }

    override fun visitChildren(node: RuleNode) {
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            if (terminal in child) {
                return child.accept(this)
            }
        }
    }
}

private fun ParseTreeLocatable.toLocationLink(originSelectionRange: TextRange): LocationLink {
    val uri = this.path.toUri().toString()
    val range = this.textRange.toLspRange()
    val selectionRange = this.simpleNameTextRange.toLspRange()
    return LocationLink(uri, range, selectionRange, originSelectionRange.toLspRange())
}
