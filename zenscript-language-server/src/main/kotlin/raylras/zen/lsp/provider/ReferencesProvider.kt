package raylras.zen.lsp.provider

import org.antlr.v4.runtime.misc.Predicate
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.antlr.v4.runtime.tree.TerminalNode
import org.eclipse.lsp4j.Location
import org.eclipse.lsp4j.ReferenceParams
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Listener
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.resolveSymbols
import raylras.zen.model.symbol.*
import raylras.zen.util.*

object ReferencesProvider {
    /**
     * find references has four steps:
     * 1. get a symbol under cursor
     * 2. get a searching rule related to the symbol ( for most symbols, it matches name, but for some special symbols like operators are different)
     * 3. search the terminal node at all documents
     * 4. get the cst at every searched node, and resolve its symbol, filtering those containing the current symbol.
     */
    fun references(unit: CompilationUnit?, params: ReferenceParams): List<Location>? {
        unit ?: return null
        val cursor = params.position.toTextPosition()
        val symbol = getSymbolOnCursor(unit, cursor) ?: return emptyList()
        val searchRule = getSymbolSearchRule(symbol)
        val list = getSearchingScope(symbol, unit).flatMap { unit ->
            val uri = unit.path.toUri().toString()
            searchPossible(searchRule, unit.parseTree).filter { cst ->
                    resolveSymbols<Symbol>(cst, unit).any { it == symbol }
            }.map { toLocation(uri, it) }
        }
        return list
    }

    private fun toLocation(uri: String, cst: ParseTree): Location {
        val location = Location()
        location.uri = uri
        location.range = cst.textRange.toLspRange()
        return location
    }

    private fun getSearchingScope(symbol: Symbol, symbolUnit: CompilationUnit): Collection<CompilationUnit> {
        if (isGloballyAccessibleSymbol(symbol)) {
            return symbolUnit.env.units.toList()
        }

        return listOf(symbolUnit)
    }

    private fun searchPossible(search: Predicate<TerminalNode>, searchingScope: ParseTree?): List<ParseTree> {
        val result: MutableList<ParseTree> = ArrayList()
        ParseTreeWalker.DEFAULT.walk(object : Listener() {
            override fun visitTerminal(node: TerminalNode) {
                if (search.test(node)) {
                    result.add(node)
                }
            }
        }, searchingScope)
        return result
    }

    private fun getSymbolSearchRule(symbol: Symbol): Predicate<TerminalNode> {
        if (symbol is OperatorFunctionSymbol) {
            val opName = symbol.simpleName
            return when (symbol.operator) {
                Operator.INDEX_GET, Operator.INDEX_SET -> Predicate { node: TerminalNode -> node.symbol.type == BRACK_OPEN && node.parent is MemberIndexExprContext }
                Operator.RANGE -> Predicate { node: TerminalNode -> (node.symbol.type == TO || node.symbol.type == DOT_DOT) && node.parent is IntRangeExprContext }
                Operator.HAS -> Predicate { node: TerminalNode -> (node.symbol.type == HAS || node.symbol.type == IN) && node.parent is BinaryExprContext }
                Operator.EQUALS -> Predicate { node: TerminalNode -> node.parent is BinaryExprContext }
                Operator.MEMBER_GET, Operator.MEMBER_SET -> Predicate { node: TerminalNode -> (node.symbol.type == DOT && node.parent is MemberAccessExprContext) }
                Operator.AS -> Predicate { node: TerminalNode ->
                    ((node.symbol.type == AS && node.parent is TypeCastExprContext)
                            || (node.symbol.type == INSTANCEOF && node.parent is InstanceOfExprContext))
                }

                Operator.FOR_IN -> Predicate { node: TerminalNode -> node.symbol.type == IN && node.parent is ForeachStatementContext }
                Operator.ERROR -> Predicate { false }
                else -> Predicate { node: TerminalNode ->
                    if (node.parent is AssignmentExprContext) {
                        return@Predicate node.symbol.text == "$opName="
                    }
                    node.parent is ExpressionContext && node.symbol.text == opName
                }
            }
        }

        val symbolName = symbol.simpleName
        return Predicate { node: TerminalNode -> node.symbol.text == symbolName }
    }

    private fun isGloballyAccessibleSymbol(symbol: Symbol): Boolean {
        // parameters and import can never be accessed by other units.
        if (symbol is ParameterSymbol || symbol is ImportSymbol) {
            return false
        }

        // classes and packages can never be accessed by other units.
        if (symbol is ClassSymbol || symbol is PackageSymbol) {
            return true
        }

        if (symbol is VariableSymbol && symbol is ParseTreeLocatable) {
            val parent = generateSequence(symbol.cst) { it.parent }.firstOrNull {
                it is ClassDeclarationContext || it is BlockStatementContext
            }

            // variables and functions in classes are accessible by other units.
            if (parent is ClassDeclarationContext) {
                return true
            }

            // variables in block statement could never be accessible by other units.
            if (parent is TopLevelElementContext) {
                return false
            }
        }

        return symbol is Modifiable && symbol.isStatic
    }

    private fun getSymbolOnCursor(unit: CompilationUnit, cursor: TextPosition): Symbol? {
        val cstStack = unit.parseTree.getCstStackAt(cursor)

        for (cst in cstStack) {
            val symbol: Symbol? = unit.symbolMap[cst]
            if (symbol != null) {
                return symbol
            }
            if (unit.scopeMap[cst] != null) {
                // if found parent scope,stop searching
                break
            }
        }
        return null
    }
}
