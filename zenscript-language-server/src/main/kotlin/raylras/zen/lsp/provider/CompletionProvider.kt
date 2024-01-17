package raylras.zen.lsp.provider

import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import org.eclipse.lsp4j.*
import raylras.zen.lsp.provider.data.Keywords
import raylras.zen.lsp.provider.data.Snippets
import raylras.zen.model.CompilationUnit
import raylras.zen.model.SemanticEntity
import raylras.zen.model.Visitor
import raylras.zen.model.brackets.bracketEntriesLocal
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.resolve.lookupScope
import raylras.zen.model.resolve.resolveSemantics
import raylras.zen.model.scope.Scope
import raylras.zen.model.symbol.*
import raylras.zen.model.type.Type
import raylras.zen.util.*
import raylras.zen.util.l10n.L10N

object CompletionProvider {
    fun completion(unit: CompilationUnit, params: CompletionParams): CompletionList {
        val visitor = CompletionVisitor(unit, params)
        unit.accept(visitor)
        return visitor.result
    }

    fun resolveCompletionItem(unresolved: CompletionItem): CompletionItem {
        // TODO: Not yet implemented
        return unresolved
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
                tailingNode?.parent?.accept(this)
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
                visit(ctx.mapEntry())
                return
            }

            // { ..., text| }
            //      ^ ____
            if (ctx.COMMA().any { leadingNode in it }) {
                visit(ctx.mapEntry())
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
                resolveSemantics(expr, unit).firstOrNull()?.let { entity: SemanticEntity ->
                    when (entity) {
                        is ClassSymbol -> {
                            appendStaticMembers(entity)
                        }

                        is Symbol -> {
                            appendInstanceMembers(entity.type)
                            appendMemberAccessSnippets(entity.type, ctx)
                        }

                        is Type -> {
                            appendInstanceMembers(entity)
                            appendMemberAccessSnippets(entity, ctx)
                        }
                    }
                }
                return
            }

            // expr.|
            // ^^^^_
            if (leadingNode in expr && tailingNode in ctx.DOT()) {
                resolveSemantics(ctx.expression(), unit).firstOrNull()?.let { entity: SemanticEntity ->
                    when (entity) {
                        is ClassSymbol -> {
                            appendStaticMembers(entity)
                        }

                        is Symbol -> {
                            appendInstanceMembers(entity.type)
                            appendMemberAccessSnippets(entity.type, ctx)
                        }

                        is Type -> {
                            appendInstanceMembers(entity)
                            appendMemberAccessSnippets(entity, ctx)
                        }
                    }
                }
                return
            }

            visitChildren(ctx)
        }

        override fun visitArrayLiteralExpr(ctx: ArrayLiteralExprContext) {
            // [ text ]
            // ^ ____
            if (leadingNode in ctx.BRACK_OPEN()) {
                visit(ctx.expression())
                return
            }

            // [ ..., text| ]
            //      ^ ____
            if (ctx.COMMA().any { leadingNode in it }) {
                visit(ctx.expression())
                return
            }

            visitChildren(ctx)
        }

        override fun visitCallExpr(ctx: CallExprContext) {
            // expr(text|)
            //     ^____
            if (leadingNode in ctx.PAREN_OPEN()) {
                visit(ctx.argument())
                return
            }

            // expr(..., text|)
            //         ^ ____
            if (ctx.COMMA().any { leadingNode in it }) {
                visit(ctx.argument())
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

        fun visit(nodes: List<ParseTree>) {
            nodes.firstOrNull { leadingNode in it || tailingNode in it }?.accept(this)
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
                scope.getSymbols().forEach { addToCompletionList(it) }
                scope = scope.parent
            }
            unit.imports.forEach { addToCompletionList(it) }
        }

        private fun appendGlobalSymbols() {
            unit.env.globals.forEach { addToCompletionList(it) }
        }

        private fun appendInstanceMembers(type: Type) {
            (type as? SymbolProvider)
                ?.getSymbols(unit.env)
                ?.filter { it is Modifiable && it.isStatic.not() }
                ?.forEach { addToCompletionList(it) }
        }

        private fun appendStaticMembers(classSymbol: ClassSymbol) {
            classSymbol.getSymbols()
                .filter { it is Modifiable && it.isStatic }
                .forEach { addToCompletionList(it) }
        }

        private fun appendTypeNames() {
            unit.imports.forEach { addToCompletionList(it) }
        }

        private fun appendKeywords(vararg keywords: String) {
            for (keyword in keywords) {
                addToCompletionList(keyword)
            }
        }

        private fun appendBracketHandlers() {
            val entries = unit.env.bracketEntriesLocal
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

        private fun appendMemberAccessSnippets(type: Type?, ctx: MemberAccessExprContext) {
            type ?: return
            sequenceOf(
                Snippets.dotFor(type, unit.env, ctx),
                Snippets.dotForI(type, unit.env, ctx),
                Snippets.dotIfNull(type, ctx),
                Snippets.dotIfNotNull(type, ctx),
                Snippets.dotVal(ctx),
                Snippets.dotVar(ctx),
            ).forEach {
                addToCompletionList(it)
            }
        }

        private fun shouldCreateCompletionItem(symbol: Symbol): Boolean {
            return when (symbol) {
                is FunctionSymbol -> true
                is VariableSymbol -> true
                is ParameterSymbol -> true
                is ImportSymbol -> true
                else -> false
            }
        }

        private fun addToCompletionList(symbol: Symbol) {
            if (shouldCreateCompletionItem(symbol).not()) return

            CompletionItem().apply {
                label = symbol.simpleName
                kind = toCompletionKind(symbol)
                labelDetails = toLabelDetails(symbol)
                if (symbol is Executable) {
                    insertTextFormat = InsertTextFormat.Snippet
                    insertText = if (symbol.parameters.isEmpty()) {
                        "$label()"
                    } else {
                        "$label($1)"
                    }
                }
            }.let {
                addToCompletionList(it)
            }
        }

        private fun addToCompletionList(keyword: String) {
            CompletionItem().apply {
                label = keyword
                detail = L10N.localize("completion_keyword")
                kind = CompletionItemKind.Keyword
            }.let {
                addToCompletionList(it)
            }
        }

        private fun addToCompletionList(item: CompletionItem?) {
            item?.let { result.items.add(it) }
        }

        private fun toLabelDetails(symbol: Symbol): CompletionItemLabelDetails {
            return CompletionItemLabelDetails().apply {
                when (symbol) {
                    is Executable -> {
                        detail = symbol.parameters.joinToString(
                            separator = ", ",
                            prefix = "(",
                            postfix = ")"
                        ) { it.simpleName + " as " + it.type.simpleTypeName }
                        description = symbol.returnType.simpleTypeName
                    }

                    is ImportSymbol -> {
                        description = symbol.simpleName
                    }

                    else -> {
                        description = symbol.type.simpleTypeName
                    }
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
    }
}
