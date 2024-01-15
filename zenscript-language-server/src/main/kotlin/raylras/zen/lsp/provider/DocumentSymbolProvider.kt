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
    fun documentSymbol(unit: CompilationUnit, params: DocumentSymbolParams): List<DocumentSymbol> {
        val visitor = DocumentSymbolVisitor()
        unit.parseTree!!.accept(visitor)
        return visitor.topLevelSymbolList
    }

    private class DocumentSymbolVisitor : Visitor<Unit>() {
        val topLevelSymbolList = ArrayList<DocumentSymbol>()
        private val stack = ArrayStack<DocumentSymbol>()

        override fun visitFunctionDeclaration(ctx: FunctionDeclarationContext) {
            enterSymbol(ctx, ctx.simpleName(), SymbolKind.Function)
        }

        override fun visitClassDeclaration(ctx: ClassDeclarationContext) {
            enterSymbol(ctx, ctx.simpleName() ?: ctx.simpleNameOrPrimitiveType(), SymbolKind.Class)
        }

        override fun visitExpandFunctionDeclaration(ctx: ExpandFunctionDeclarationContext) {
            enterSymbol(ctx, ctx.simpleName(), SymbolKind.Function)
        }

        override fun visitConstructorDeclaration(ctx: ConstructorDeclarationContext) {
            enterSymbol(ctx, ctx.ZEN_CONSTRUCTOR(), SymbolKind.Constructor)
        }

        override fun visitVariableDeclaration(ctx: VariableDeclarationContext) {
            enterSymbol(ctx, ctx.simpleName(), SymbolKind.Variable)
        }

        override fun visitFunctionExpr(ctx: FunctionExprContext) {
            enterSymbol(ctx, ctx.FUNCTION(), SymbolKind.Function)
        }

        private fun enterSymbol(
            encloseCtx: RuleNode,
            selectionCtx: ParseTree?,
            kind: SymbolKind,
            name: String? = selectionCtx?.text
        ) {
            selectionCtx ?: return
            name ?: return
            toDocumentSymbol(encloseCtx, selectionCtx, name, kind).let {
                push(it)
                visitChildren(encloseCtx)
                pop()
            }
        }

        private fun toDocumentSymbol(
            encloseCtx: ParseTree,
            selectionCtx: ParseTree,
            name: String,
            kind: SymbolKind
        ): DocumentSymbol {
            val encloseRange = encloseCtx.textRange.toLspRange()
            val selectionRange = selectionCtx.textRange.toLspRange()
            return DocumentSymbol(name, kind, encloseRange, selectionRange)
        }

        private fun push(symbol: DocumentSymbol) {
            if (isTopLevel) {
                addToTopLevelSymbolList(symbol)
            } else {
                addToCurrentSymbolChildren(symbol)
            }
            stack.push(symbol)
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
