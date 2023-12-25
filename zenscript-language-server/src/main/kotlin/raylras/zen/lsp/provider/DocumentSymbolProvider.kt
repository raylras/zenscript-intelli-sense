package raylras.zen.lsp.provider

import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.eclipse.lsp4j.DocumentSymbol
import org.eclipse.lsp4j.DocumentSymbolParams
import org.eclipse.lsp4j.SymbolKind
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.util.ArrayStack
import raylras.zen.util.textRange
import raylras.zen.util.toLspRange

object DocumentSymbolProvider {
    fun documentSymbol(unit: CompilationUnit?, params: DocumentSymbolParams): List<DocumentSymbol>? {
        unit ?: return null
        val visitor = DocumentSymbolVisitor()
        unit.parseTree?.accept(visitor)
        return visitor.topLevelSymbolList
    }

    private class DocumentSymbolVisitor : Visitor<DocumentSymbol?>() {
        val topLevelSymbolList = ArrayList<DocumentSymbol>()
        private val stack = ArrayStack<DocumentSymbol>()

        override fun visitFunctionDeclaration(ctx: FunctionDeclarationContext): DocumentSymbol {
            return enter(ctx, ctx.simpleName(), SymbolKind.Function)
        }

        override fun visitClassDeclaration(ctx: ClassDeclarationContext): DocumentSymbol {
            return enter(ctx, ctx.simpleNameOrPrimitiveType(), SymbolKind.Class)
        }

        override fun visitExpandFunctionDeclaration(ctx: ExpandFunctionDeclarationContext): DocumentSymbol {
            return enter(ctx, ctx.simpleName(), SymbolKind.Function)
        }

        override fun visitConstructorDeclaration(ctx: ConstructorDeclarationContext): DocumentSymbol {
            return enter(ctx, ctx.ZEN_CONSTRUCTOR(), SymbolKind.Constructor)
        }

        override fun visitVariableDeclaration(ctx: VariableDeclarationContext): DocumentSymbol {
            return enter(ctx, ctx.simpleName(), SymbolKind.Variable)
        }

        override fun visitFunctionExpr(ctx: FunctionExprContext): DocumentSymbol {
            return enter(ctx, ctx.FUNCTION(), SymbolKind.Function)
        }

        private fun enter(enclose: RuleNode, selection: ParseTree, kind: SymbolKind): DocumentSymbol {
            val symbol = toDocumentSymbol(enclose, selection, kind)
            if (push(symbol)) {
                visitChildren(enclose)
                pop()
            }
            return symbol
        }

        private fun toDocumentSymbol(enclose: ParseTree, selection: ParseTree, kind: SymbolKind): DocumentSymbol {
            return toDocumentSymbol(enclose, selection, selection.text, kind)
        }

        private fun toDocumentSymbol(
            enclose: ParseTree,
            selection: ParseTree,
            name: String,
            kind: SymbolKind
        ): DocumentSymbol {
            val encloseRange = enclose.textRange.toLspRange()
            val selectionRange = selection.textRange.toLspRange()
            return DocumentSymbol(name, kind, encloseRange, selectionRange)
        }

        private fun push(symbol: DocumentSymbol): Boolean {
            if (symbol.name.isEmpty()) {
                return false
            }
            if (isTopLevel) {
                addToTopLevelSymbolList(symbol)
            } else {
                addToCurrentSymbolChildren(symbol)
            }
            stack.push(symbol)
            return true
        }

        private fun pop() {
            stack.pop()
        }

        private val isTopLevel: Boolean
            get() = stack.isEmpty()

        private fun addToTopLevelSymbolList(symbol: DocumentSymbol) {
            topLevelSymbolList.add(symbol)
        }

        private fun addToCurrentSymbolChildren(symbol: DocumentSymbol) {
            val parent = stack.peek() ?: return
            if (parent.children == null) {
                parent.children = ArrayList()
            }
            parent.children.add(symbol)
        }
    }
}

