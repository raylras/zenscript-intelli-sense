package raylras.zen.lsp.provider

import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import org.eclipse.lsp4j.DefinitionParams
import org.eclipse.lsp4j.LocationLink
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveSemantics
import raylras.zen.model.resolve.resolveType
import raylras.zen.model.symbol.Executable
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.filterOverloads
import raylras.zen.model.type.AnyType
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
        result = resolveSemantics(ctx, unit)
            .filterIsInstance<ParseTreeLocatable>()
            .map { it.toLocationLink(ctx.textRange) }
    }

    override fun visitSimpleNameExpr(ctx: SimpleNameExprContext) {
        result = resolveSemantics(ctx, unit)
            .filterIsInstance<ParseTreeLocatable>()
            .map { it.toLocationLink(ctx.textRange) }
    }

    override fun visitMemberAccessExpr(ctx: MemberAccessExprContext) {
        when (terminal) {
            in ctx.simpleName() -> {
                val candidates = resolveSemantics(ctx, unit).toList()
                when (candidates.size) {
                    0 -> {
                        result = emptySequence()
                        return
                    }
                    1 -> {
                        val single = candidates.single()
                        result = when (single) {
                            is ParseTreeLocatable -> sequenceOf(single.toLocationLink(ctx.simpleName().textRange))
                            else -> emptySequence()
                        }
                        return
                    }

                    else -> {
                        val parent = ctx.parent
                        if (parent is CallExprContext) {
                            val actualArgTypes = parent.argument().map { resolveType(it, unit) ?: AnyType }
                            candidates.asSequence()
                                .filterIsInstance<Executable>()
                                .filterOverloads(actualArgTypes, unit.env)
                                .filterIsInstance<ParseTreeLocatable>()
                                .takeIf { it.iterator().hasNext() }
                                ?.let { overloaded ->
                                    result = overloaded.map { it.toLocationLink(ctx.simpleName().textRange) }
                                    return
                                }
                        }
                        result = candidates.asSequence()
                            .filterIsInstance<ParseTreeLocatable>()
                            .map { it.toLocationLink(ctx.simpleName().textRange) }
                        return
                    }
                }
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
