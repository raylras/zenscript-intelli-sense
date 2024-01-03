package raylras.zen.lsp.provider

import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import org.eclipse.lsp4j.*
import raylras.zen.lsp.bracket.BracketHandlerService
import raylras.zen.lsp.provider.data.Keywords
import raylras.zen.lsp.provider.data.Snippet
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.getType
import raylras.zen.model.resolve.lookupScope
import raylras.zen.model.scope.Scope
import raylras.zen.model.symbol.*
import raylras.zen.model.type.Type
import raylras.zen.util.*
import raylras.zen.util.l10n.L10N

object CompletionProvider {
    fun completion(unit: CompilationUnit?, params: CompletionParams): CompletionList? {
        unit ?: return null
        val visitor = CompletionVisitor(unit, params)
        unit.accept(visitor)
        return visitor.result
    }

    private class CompletionVisitor(val unit: CompilationUnit, params: CompletionParams) : Visitor<Unit>() {
        private val cursor: TextPosition = params.position.toTextPosition()
        private val tailingNode: TerminalNode? = unit.parseTree.getTerminalAt(cursor)
        private val leadingNode: TerminalNode? = tailingNode.getPrev(unit.tokenStream)
        private val tailingText: String = tailingNode?.text ?: ""
        private val leadingText: String = leadingNode?.text ?: ""
        val result = CompletionList()

        /*
            | represents the cursor
            ^ represents the leading terminal node
            _ represents the tailing terminal node
         */
        override fun visitImportDeclaration(ctx: ImportDeclarationContext) {
            // import text|
            // ^^^^^^ ____
            if (leadingNode in ctx.IMPORT()) {
                appendImports()
                return
            }

            // import foo.|
            //        ^^^_
            if (tailingNode is ErrorNode && tailingText == ".") {
                appendImports()
                return
            }

            // import foo.text|
            //           ^____
            if (leadingText == ".") {
                appendImports()
                return
            }

            // import foo.|bar
            //        ^^^_
            if (tailingText == ".") {
                appendImports()
                return
            }

            // import foo.bar text|
            //            ^^^ ____
            if (leadingNode in ctx.qualifiedName() && tailingNode !in ctx.qualifiedName()) {
                appendKeywords(Keywords.AS)
                return
            }

            // import foo.bar; text|
            //               ^ ____
            if (leadingNode in ctx.SEMICOLON()) {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.TOPLEVEL_STATEMENT)
                return
            }
        }

        override fun visitSimpleName(ctx: SimpleNameContext) {
            appendLocalSymbols()
            appendGlobalSymbols()
            return
        }

        override fun visitFormalParameter(ctx: FormalParameterContext) {
            // name text|
            // ^^^^ ____
            if (leadingNode in ctx.simpleName()) {
                appendKeywords(Keywords.AS)
                return
            }

            // name as text|
            //      ^^ ____
            if (leadingNode in ctx.AS()) {
                appendTypeNames()
                return
            }
        }

        override fun visitFunctionBody(ctx: FunctionBodyContext) {
            // { text| }
            // ^ ____
            if (leadingNode in ctx.BRACE_OPEN()) {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.STATEMENT)
                return
            }

            visitChildren(ctx)
        }

        override fun visitClassBody(ctx: ClassBodyContext) {
            // { } text|
            //   ^ ____
            if (leadingNode in ctx.BRACE_CLOSE()) {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.CLASS_BODY)
                return
            }

            // { text| }
            // ^ ____
            if (leadingNode in ctx.BRACE_OPEN()) {
                appendKeywords(*Keywords.CLASS_BODY)
                return
            }

            visitChildren(ctx)
        }

        override fun visitClassMemberDeclaration(ctx: ClassMemberDeclarationContext) {
            // } text|    ; test|    expr text|
            // ^ ____     ^ ____     ^^^^ ____
            if (leadingNode in ctx.stop) {
                appendKeywords(*Keywords.CLASS_BODY)
                return
            }

            visitChildren(ctx)
        }

        override fun visitVariableDeclaration(ctx: VariableDeclarationContext) {
            // var name text|
            //     ^^^^ ____
            if (leadingNode in ctx.simpleName()) {
                appendKeywords(Keywords.AS)
                return
            }

            // var name as text|
            //          ^^ ____
            if (leadingNode in ctx.AS()) {
                appendTypeNames()
                return
            }

            // var name as type =|
            //                  ^
            if (leadingNode in ctx.ASSIGN() && tailingNode !in ctx.initializer) {
                appendLocalSymbols()
                appendGlobalSymbols()
                return
            }

            // var name as type = text|
            //                  ^ ____
            if (leadingNode in ctx.ASSIGN() && tailingNode in ctx.initializer) {
                visit(ctx.initializer)
                return
            }

            // var name; text|
            //         ^ ____
            if (leadingNode in ctx.SEMICOLON()) {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.STATEMENT)
                return
            }

            visitChildren(ctx)
        }

        override fun visitBlockStatement(ctx: BlockStatementContext) {
            // { text| }
            // ^ ____
            if (leadingNode in ctx.BRACE_OPEN()) {
                visitParent(tailingNode)
                appendKeywords(*Keywords.STATEMENT)
                return
            }

            // { } text|
            //   ^ ____
            if (leadingNode in ctx.BRACE_CLOSE()) {
                visitParent(tailingNode)
                appendKeywords(*Keywords.STATEMENT)
                return
            }

            visitChildren(ctx)
        }

        override fun visitReturnStatement(ctx: ReturnStatementContext) {
            // return text|
            // ^^^^^^ ____
            if (leadingNode in ctx.RETURN()) {
                visit(ctx.expression())
                return
            }

            // return; text|
            //       ^ ____
            if (leadingNode in ctx.SEMICOLON()) {
                visitParent(tailingNode)
                appendKeywords(*Keywords.STATEMENT)
                return
            }

            visitChildren(ctx)
        }

        override fun visitIfStatement(ctx: IfStatementContext) {
            // if text|
            // ^^ ____
            if (leadingNode in ctx.IF()) {
                appendLocalSymbols()
                appendGlobalSymbols()
                return
            }

            visitChildren(ctx)
        }

        override fun visitForeachBody(ctx: ForeachBodyContext) {
            // { text| }
            // ^ ____
            if (leadingNode in ctx.BRACE_OPEN()) {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.STATEMENT)
                return
            }

            // { } text|
            //   ^ ____
            if (leadingNode in ctx.BRACE_CLOSE()) {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.STATEMENT)
                return
            }

            visitChildren(ctx)
        }

        override fun visitWhileStatement(ctx: WhileStatementContext) {
            // while expr|
            // ^^^^^
            if (leadingNode in ctx.WHILE()) {
                visit(ctx.expression())
                return
            }

            visitChildren(ctx)
        }

        override fun visitExpressionStatement(ctx: ExpressionStatementContext) {
            // text|
            // ____
            if (ctx.expression() is SimpleNameExprContext && tailingNode in ctx.expression()) {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.STATEMENT)
                return
            }

            // expr; text|
            //     ^ ____
            if (leadingNode in ctx.SEMICOLON()) {
                visitParent(tailingNode)
                appendKeywords(*Keywords.STATEMENT)
                return
            }

            visitChildren(ctx)
        }

        override fun visitAssignmentExpr(ctx: AssignmentExprContext) {
            // expr = text|
            //      ^ ____
            if (leadingNode in ctx.op) {
                appendLocalSymbols()
                appendGlobalSymbols()
                return
            }

            // expr =|
            // ^^^^ _
            if (ctx.left !is MemberAccessExprContext && leadingNode in ctx.left) {
                appendLocalSymbols()
                appendGlobalSymbols()
                return
            }

            visitChildren(ctx)
        }

        override fun visitMapLiteralExpr(ctx: MapLiteralExprContext) {
            // { text| }
            // ^ ____
            if (leadingNode in ctx.BRACE_OPEN()) {
                visit(ctx.mapEntryList())
                return
            }

            // { expr| }
            //
            if (leadingNode in ctx.mapEntryList()) {
                visit(ctx.mapEntryList())
                return
            }

            visitChildren(ctx)
        }

        override fun visitSimpleNameExpr(ctx: SimpleNameExprContext) {
            appendLocalSymbols()
            appendGlobalSymbols()
            return
        }

        override fun visitBinaryExpr(ctx: BinaryExprContext) {
            // expr + text|
            //      ^ ____
            if (leadingNode in ctx.op) {
                appendLocalSymbols()
                appendGlobalSymbols()
                return
            }
        }

        override fun visitParensExpr(ctx: ParensExprContext) {
            // (text|)
            // ^____
            if (leadingNode in ctx.PAREN_OPEN()) {
                visit(ctx.expression())
                return
            }

            visitChildren(ctx)
        }

        override fun visitBracketHandlerExpr(ctx: BracketHandlerExprContext) {
            // <|
            // _
            if (tailingNode in ctx.LESS_THEN()) {
                appendBracketHandlers()
                return
            }

            // <text|
            // ^____
            if (leadingNode in ctx.LESS_THEN() && tailingNode in ctx.raw()) {
                appendBracketHandlers()
                return
            }
        }

        override fun visitUnaryExpr(ctx: UnaryExprContext) {
            // !text|
            // ^____
            if (leadingNode in (ctx.op)) {
                appendLocalSymbols()
                appendGlobalSymbols()
                return
            }

            visitChildren(ctx)
        }

        override fun visitTernaryExpr(ctx: TernaryExprContext) {
            // expr ? text|
            //      ^ ____
            if (leadingNode in ctx.QUESTION()) {
                visit(ctx.truePart)
                return
            }

            // expr ? expr : text|
            //             ^ ____
            if (leadingNode in ctx.COLON()) {
                visit(ctx.falsePart)
                return
            }

            visitChildren(ctx)
        }

        override fun visitMemberAccessExpr(ctx: MemberAccessExprContext) {
            val expr: ExpressionContext = ctx.expression()

            // expr.text|
            //     ^____
            if (leadingNode in ctx.DOT()) {
                getType(expr, unit).let { type ->
                    appendMembers(type)
                    appendMemberAccessSnippets(type, ctx)
                    return
                }
            }

            // expr.|
            // ^^^^_
            if (leadingNode in expr && tailingNode in ctx.DOT()) {
                getType(expr, unit).let { type ->
                    appendMembers(type)
                    appendMemberAccessSnippets(type, ctx)
                    return
                }
            }

            visitChildren(ctx)
        }

        override fun visitArrayLiteralExpr(ctx: ArrayLiteralExprContext) {
            // [ text ]
            // ^ ____
            if (leadingNode in ctx.BRACK_OPEN()) {
                visit(ctx.expressionList())
                return
            }

            visitChildren(ctx)
        }

        override fun visitCallExpr(ctx: CallExprContext) {
            // expr(text|)
            //     ^____
            if (leadingNode in ctx.PAREN_OPEN()) {
                visit(ctx.expressionList())
                return
            }

            // expr(expr,|)
            //          ^
            if (leadingNode is ErrorNode) {
                appendLocalSymbols()
                appendGlobalSymbols()
                return
            }

            visitChildren(ctx)
        }

        override fun visitExpressionList(ctx: ExpressionListContext) {
            // expr, text|
            //     ^ ____
            if (leadingNode in ctx.COMMA()) {
                visitParent(tailingNode)
                return
            }

            // expr,|
            // ^^^^_
            if (tailingNode in ctx.COMMA()) {
                appendLocalSymbols()
                appendGlobalSymbols()
                return
            }

            visitChildren(ctx)
        }

        override fun visitMapEntry(ctx: MapEntryContext) {
            // text|
            // ____
            if (tailingNode in (ctx.key)) {
                visit(ctx.key)
                return
            }

            // expr : text|
            //      ^ ____
            if (leadingNode in ctx.COLON()) {
                visit(ctx.value)
                return
            }

            visitChildren(ctx)
        }

        override fun visit(node: ParseTree?) {
            return node?.accept(this) ?: Unit
        }

        override fun visitChildren(node: RuleNode) {
            for (i in 0 until node.childCount) {
                val child = node.getChild(i)
                if (leadingNode in child || tailingNode in child) {
                    child.accept(this)
                    break
                }
            }
        }

        fun visitParent(node: ParseTree?) {
            node?.parent?.accept(this)
        }

        private fun appendImports() {
            // FIXME: appendImports
        }

        private fun appendLocalSymbols() {
            var scope: Scope? = lookupScope(tailingNode, unit)
            while (scope != null) {
                scope.getSymbols()
                    .map { createCompletionItem(it) }
                    .forEach { addToCompletionList(it) }
                scope = scope.parent
            }
        }

        private fun appendGlobalSymbols() {
            unit.env.globals
                .map { createCompletionItem(it) }
                .forEach { addToCompletionList(it) }
        }

        private fun appendMembers(type: Type) {
            if (type is SymbolProvider) {
                type.getSymbols(unit.env)
                    .filter { shouldCreateCompletionItem(it) }
                    .map { createCompletionItem(it) }
                    .forEach { addToCompletionList(it) }
            }
        }

        private fun appendTypeNames() {
            unit.imports
                .map { createCompletionItem(it) }
                .forEach { addToCompletionList(it) }
        }

        private fun appendKeywords(vararg keywords: String?) {
            for (keyword in keywords) {
                addToCompletionList(createCompletionItem(keyword))
            }
        }

        private fun appendBracketHandlers() {
            val service = BracketHandlerService.getInstance(unit.env)
            val entries = service.entriesLocal
            entries.forEach { entry ->
                entry.getStringOrNull("_id")?.let { id ->
                    val item = CompletionItem().apply {
                        label = id
                        kind = CompletionItemKind.Value
                        insertText = "$id>"
                        entry.getStringOrNull("_name")?.let { name ->
                            labelDetails = CompletionItemLabelDetails().apply {
                                description = name
                            }
                        }
                    }
                    addToCompletionList(item)
                }
            }
            entries.forEach { entry ->
                entry.getStringOrNull("_name")?.let { name ->
                    entry.getStringOrNull("_id")?.let { id ->
                        val item = CompletionItem().apply {
                            label = name
                            kind = CompletionItemKind.Value
                            insertText = "$id>"
                            labelDetails = CompletionItemLabelDetails().apply {
                                description = id
                            }
                        }
                        addToCompletionList(item)
                    }
                }
            }
        }

        private fun appendMemberAccessSnippets(type: Type?, ctx: MemberAccessExprContext?) {
            if (type == null || ctx == null) return
            appendSnippet(Snippet.dotFor(type, unit.env, ctx))
            appendSnippet(Snippet.dotForI(type, unit.env, ctx))
            appendSnippet(Snippet.dotIfNull(type, ctx))
            appendSnippet(Snippet.dotIfNotNull(type, ctx))
            appendSnippet(Snippet.dotVal(ctx))
            appendSnippet(Snippet.dotVar(ctx))
        }

        private fun appendSnippet(item: CompletionItem?) {
            if (item != null) {
                item.kind = CompletionItemKind.Snippet
                addToCompletionList(item)
            }
        }

        private fun shouldCreateCompletionItem(symbol: Symbol): Boolean {
            return when( symbol) {
                is Executable -> true
                is VariableSymbol -> true
                is ParameterSymbol -> true
                else -> false
            }
        }

        private fun createCompletionItem(symbol: Symbol): CompletionItem {
            val item = CompletionItem(symbol.simpleName)
            item.kind = toCompletionKind(symbol)
            item.labelDetails = createLabelDetails(symbol)
            if (symbol is Executable) {
                item.insertTextFormat = InsertTextFormat.Snippet
                if (symbol.parameters.isEmpty()) {
                    item.insertText = item.label + "()"
                } else {
                    item.insertText = item.label + "($1)"
                }
            }
            return item
        }

        private fun createCompletionItem(keyword: String?): CompletionItem {
            val item = CompletionItem(keyword)
            item.detail = L10N.localize("completion_keyword")
            item.kind = CompletionItemKind.Keyword
            return item
        }

        private fun createLabelDetails(symbol: Symbol): CompletionItemLabelDetails {
            when (symbol) {
                is Executable -> {
                    return CompletionItemLabelDetails().apply {
                        detail = symbol.parameters.joinToString(
                            separator = ", ",
                            prefix = "(",
                            postfix = ")"
                        ) { it.simpleName + " as " + it.type.simpleTypeName }
                        description = symbol.returnType.simpleTypeName
                    }
                }

                else -> {
                    val labelDetails = CompletionItemLabelDetails()
                    val type: String = symbol.type.simpleTypeName
                    labelDetails.description = type
                    return labelDetails
                }
            }
        }

        private fun toCompletionKind(symbol: Symbol): CompletionItemKind? {
            return when (symbol) {
                is ImportSymbol, is ClassSymbol -> CompletionItemKind.Class
                is Executable -> CompletionItemKind.Function
                is VariableSymbol, is ParameterSymbol -> CompletionItemKind.Variable
                else -> null
            }
        }

        private fun addToCompletionList(item: CompletionItem?) {
            result.items.add(item)
        }
    }
}
