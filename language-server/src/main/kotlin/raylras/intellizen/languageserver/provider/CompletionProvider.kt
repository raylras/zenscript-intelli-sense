package raylras.intellizen.languageserver.provider

import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode
import org.eclipse.lsp4j.*
import raylras.intellizen.CompilationUnit
import raylras.intellizen.Visitor
import raylras.intellizen.brackets.bracketEntriesLocal
import raylras.intellizen.i18n.L10N
import raylras.intellizen.languageserver.provider.data.Keywords
import raylras.intellizen.languageserver.provider.data.Snippets
import raylras.intellizen.languageserver.util.toTextPosition
import raylras.intellizen.parser.ZenScriptParser.*
import raylras.intellizen.resolve.lookupScope
import raylras.intellizen.resolve.resolveSemantics
import raylras.intellizen.symbol.*
import raylras.intellizen.type.Type
import raylras.intellizen.util.TextPosition
import raylras.intellizen.util.contains
import raylras.intellizen.util.getPrev
import raylras.intellizen.util.getTerminalAt

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
        when {
            // import text|
            // ^^^^^^ ____
            leadingNode in ctx.IMPORT() -> {
                unit.env.rootPackage.getSymbols().forEach { addToCompletionList(it) }
            }

            // import foo.|    import foo.|bar
            //        ^^^_            ^^^_
            tailingText == "." -> {
                val pkg = ctx.qualifiedName().getTextUntil(tailingNode).split('.')
                toSymbolProvider(pkg).getSymbols()
                    .filter { it is PackageSymbol || it is ClassSymbol || it.isStatic }
                    .forEach { addToCompletionList(it) }
            }

            // import foo.text|
            //           ^____
            leadingText == "." -> {
                val pkg = ctx.qualifiedName().getTextUntil(leadingNode).split('.')
                toSymbolProvider(pkg).getSymbols()
                    .filter { it is PackageSymbol || it is ClassSymbol || it.isStatic }
                    .forEach { addToCompletionList(it) }
            }

            // import foo.bar text|
            //            ^^^ ____
            leadingNode in ctx.qualifiedName() && tailingNode !in ctx.qualifiedName() -> {
                appendKeywords(Keywords.AS)
            }

            // import foo.bar; text|
            //               ^ ____
            leadingNode in ctx.SEMICOLON() -> {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.TOPLEVEL_STATEMENT)
            }
        }
    }

    override fun visitSimpleName(ctx: SimpleNameContext) {
        appendLocalSymbols()
        appendGlobalSymbols()
    }

    override fun visitFormalParameter(ctx: FormalParameterContext) {
        when {
            // name text|
            // ^^^^ ____
            leadingNode in ctx.simpleName() -> {
                appendKeywords(Keywords.AS)
            }

            // name as text|
            //      ^^ ____
            leadingNode in ctx.AS() -> {
                appendTypeNames()
            }
        }
    }

    override fun visitFunctionBody(ctx: FunctionBodyContext) {
        when {
            // { text| }
            // ^ ____
            leadingNode in ctx.BRACE_OPEN() -> {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.STATEMENT)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitClassBody(ctx: ClassBodyContext) {
        when {
            // { } text|
            //   ^ ____
            leadingNode in ctx.BRACE_CLOSE() -> {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.CLASS_BODY)
            }
            // { text| }
            // ^ ____
            leadingNode in ctx.BRACE_OPEN() -> {
                appendKeywords(*Keywords.CLASS_BODY)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitClassMemberDeclaration(ctx: ClassMemberDeclarationContext) {
        when {
            // } text|    ; test|    expr text|
            // ^ ____     ^ ____     ^^^^ ____
            leadingNode in ctx.stop -> {
                appendKeywords(*Keywords.CLASS_BODY)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitVariableDeclaration(ctx: VariableDeclarationContext) {
        when {
            // var name text|
            //     ^^^^ ____
            leadingNode in ctx.simpleName() -> {
                appendKeywords(Keywords.AS)
            }

            // var name as text|
            //          ^^ ____
            leadingNode in ctx.AS() -> {
                appendTypeNames()
            }

            // var name as type =|
            //                  ^
            leadingNode in ctx.ASSIGN() && tailingNode !in ctx.initializer -> {
                appendLocalSymbols()
                appendGlobalSymbols()
            }

            // var name as type = text|
            //                  ^ ____
            leadingNode in ctx.ASSIGN() && tailingNode in ctx.initializer -> {
                visit(ctx.initializer)
            }

            // var name; text|
            //         ^ ____
            leadingNode in ctx.SEMICOLON() -> {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.STATEMENT)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitBlockStatement(ctx: BlockStatementContext) {
        when {
            // { text| }
            // ^ ____
            leadingNode in ctx.BRACE_OPEN() -> {
                visit(tailingNode?.parent)
                appendKeywords(*Keywords.STATEMENT)
            }

            // { } text|
            //   ^ ____
            leadingNode in ctx.BRACE_CLOSE() -> {
                visit(tailingNode?.parent)
                appendKeywords(*Keywords.STATEMENT)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitReturnStatement(ctx: ReturnStatementContext) {
        when {
            // return text|
            // ^^^^^^ ____
            leadingNode in ctx.RETURN() -> {
                visit(ctx.expression())
            }

            // return; text|
            //       ^ ____
            leadingNode in ctx.SEMICOLON() -> {
                visit(tailingNode?.parent)
                appendKeywords(*Keywords.STATEMENT)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitIfStatement(ctx: IfStatementContext) {
        when {
            // if text|
            // ^^ ____
            leadingNode in ctx.IF() -> {
                appendLocalSymbols()
                appendGlobalSymbols()
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitForeachBody(ctx: ForeachBodyContext) {
        when {
            // { text| }
            // ^ ____
            leadingNode in ctx.BRACE_OPEN() -> {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.STATEMENT)
            }

            // { } text|
            //   ^ ____
            leadingNode in ctx.BRACE_CLOSE() -> {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.STATEMENT)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitWhileStatement(ctx: WhileStatementContext) {
        when {
            // while expr|
            // ^^^^^
            leadingNode in ctx.WHILE() -> {
                visit(ctx.expression())
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitExpressionStatement(ctx: ExpressionStatementContext) {
        when {
            // text|
            // ____
            ctx.expression() is SimpleNameExprContext && tailingNode in ctx.expression() -> {
                appendLocalSymbols()
                appendGlobalSymbols()
                appendKeywords(*Keywords.STATEMENT)
            }

            // expr; text|
            //     ^ ____
            leadingNode in ctx.SEMICOLON() -> {
                visit(tailingNode?.parent)
                appendKeywords(*Keywords.STATEMENT)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitAssignmentExpr(ctx: AssignmentExprContext) {
        when {
            // expr = text|
            //      ^ ____
            leadingNode in ctx.op -> {
                appendLocalSymbols()
                appendGlobalSymbols()
            }

            // expr =|
            // ^^^^ _
            ctx.left !is MemberAccessExprContext && leadingNode in ctx.left -> {
                appendLocalSymbols()
                appendGlobalSymbols()
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitMapLiteralExpr(ctx: MapLiteralExprContext) {
        when {
            // { text| }
            // ^ ____
            leadingNode in ctx.BRACE_OPEN() -> {
                visit(ctx.mapEntry())
            }

            // { ..., text| }
            //      ^ ____
            ctx.COMMA().any { leadingNode in it } -> {
                visit(ctx.mapEntry())
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitSimpleNameExpr(ctx: SimpleNameExprContext) {
        appendLocalSymbols()
        appendGlobalSymbols()
    }

    override fun visitBinaryExpr(ctx: BinaryExprContext) {
        when {
            // expr + text|
            //      ^ ____
            leadingNode in ctx.op -> {
                appendLocalSymbols()
                appendGlobalSymbols()
            }
        }
    }

    override fun visitParensExpr(ctx: ParensExprContext) {
        when {
            // (text|)
            // ^____
            leadingNode in ctx.PAREN_OPEN() -> {
                visit(ctx.expression())
            }

            // (expr)text|
            //      ^____
            leadingNode in ctx.PAREN_CLOSE() -> {
                visit(tailingNode?.parent)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitBracketHandlerExpr(ctx: BracketHandlerExprContext) {
        when {
            // <|
            // _
            tailingNode in ctx.LESS_THEN() -> {
                appendBracketHandlers()
            }

            // <text|
            // ^____
            leadingNode in ctx.LESS_THEN() && tailingNode in ctx.raw() -> {
                appendBracketHandlers()
            }
        }
    }

    override fun visitUnaryExpr(ctx: UnaryExprContext) {
        when {
            // !text|
            // ^____
            leadingNode in ctx.op -> {
                appendLocalSymbols()
                appendGlobalSymbols()
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitTernaryExpr(ctx: TernaryExprContext) {
        when {
            // expr ? text|
            //      ^ ____
            leadingNode in ctx.QUESTION() -> {
                visit(ctx.truePart)
            }

            // expr ? expr : text|
            //             ^ ____
            leadingNode in ctx.COLON() -> {
                visit(ctx.falsePart)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitMemberAccessExpr(ctx: MemberAccessExprContext) {
        when {
            // expr.text|
            //     ^____
            leadingNode in ctx.DOT() -> {
                resolveSemantics(ctx.expression(), unit).firstOrNull()?.let { expr ->
                    when {
                        expr is SymbolProvider -> {
                            appendSymbols(expr)
                        }

                        expr is Symbol && expr.type is SymbolProvider -> {
                            appendSymbols(expr.type as SymbolProvider)
                            appendMemberAccessSnippets(expr.type, ctx)
                        }
                    }
                }
            }

            // expr.|
            // ^^^^_
            leadingNode in ctx.expression() && tailingNode in ctx.DOT() -> {
                resolveSemantics(ctx.expression(), unit).firstOrNull()?.let { expr ->
                    when {
                        expr is SymbolProvider -> {
                            appendSymbols(expr)
                        }

                        expr is Symbol && expr.type is SymbolProvider -> {
                            appendSymbols(expr.type as SymbolProvider)
                            appendMemberAccessSnippets(expr.type, ctx)
                        }
                    }
                }
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitArrayLiteralExpr(ctx: ArrayLiteralExprContext) {
        when {
            // [ text ]
            // ^ ____
            leadingNode in ctx.BRACK_OPEN() -> {
                visit(ctx.expression())
            }

            // [ ..., text| ]
            //      ^ ____
            ctx.COMMA().any { leadingNode in it } -> {
                visit(ctx.expression())
            }

            else -> visitChildren(ctx)
        }

    }

    override fun visitCallExpr(ctx: CallExprContext) {
        when {
            // expr(text|)
            //     ^____
            leadingNode in ctx.PAREN_OPEN() -> {
                visit(ctx.argument())
            }

            // expr(..., text|)
            //         ^ ____
            ctx.COMMA().any { leadingNode in it } -> {
                visit(ctx.argument())
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visitMapEntry(ctx: MapEntryContext) {
        when {
            // text|
            // ____
            tailingNode in ctx.key -> {
                visit(ctx.key)
            }

            // expr : text|
            //      ^ ____
            leadingNode in ctx.COLON() -> {
                visit(ctx.value)
            }

            else -> visitChildren(ctx)
        }
    }

    override fun visit(tree: ParseTree?) {
        tree?.accept(this)
    }

    fun visit(trees: List<ParseTree>) {
        trees.firstOrNull { leadingNode in it || tailingNode in it }?.accept(this)
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

    private fun toSymbolProvider(pkg: List<String>): SymbolProvider {
        var provider: SymbolProvider = unit.env.rootPackage
        for (p in pkg) {
            provider = provider.getSymbols()
                .firstOrNull { it.simpleName == p } as? SymbolProvider
                ?: SymbolProvider.EMPTY
        }
        return provider
    }

    private fun appendLocalSymbols() {
        var scope = lookupScope(tailingNode, unit)
        while (scope != null) {
            scope.symbols.forEach { addToCompletionList(it) }
            scope = scope.parent
        }
    }

    private fun appendGlobalSymbols() {
        unit.env.globals.forEach { addToCompletionList(it) }
    }

    private fun appendSymbols(provider: SymbolProvider) {
        provider.getSymbols(unit.env).forEach { addToCompletionList(it) }
    }

    private fun appendTypeNames() {
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
            is PackageSymbol -> true
            is ClassSymbol -> true
            else -> false
        }
    }

    private fun addToCompletionList(symbol: Symbol) {
        if (shouldCreateCompletionItem(symbol).not()) return

        CompletionItem().apply {
            label = symbol.simpleName
            kind = symbol.completionKind
            labelDetails = symbol.labelDetails
//            if (symbol is Executable) {
//                insertTextFormat = InsertTextFormat.Snippet
//                insertText = if (symbol.parameters.isEmpty()) {
//                    "$label()"
//                } else {
//                    "$label($1)"
//                }
//            }
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
}

private val Symbol.completionKind: CompletionItemKind?
    get() = when (this) {
        is ImportSymbol, is ClassSymbol -> CompletionItemKind.Class
        is Executable -> CompletionItemKind.Function
        is VariableSymbol, is ParameterSymbol -> CompletionItemKind.Variable
        else -> null
    }

private val Symbol.labelDetails: CompletionItemLabelDetails
    get() = CompletionItemLabelDetails().also { label ->
        when (this) {
            is Executable -> {
                label.detail = parameters.joinToString(
                    separator = ", ",
                    prefix = "(",
                    postfix = ")"
                ) { it.simpleName + " as " + it.type.simpleTypeName }
                label.description = returnType.simpleTypeName
            }

            is ImportSymbol -> {
                label.description = qualifiedName
            }

            is PackageSymbol -> {

            }

            is ClassSymbol -> {

            }

            else -> {
                label.description = type.simpleTypeName
            }
        }
    }

private fun QualifiedNameContext.getTextUntil(terminal: TerminalNode?): String {
    val builder = StringBuilder()
    for (child in this.children) {
        if (child in terminal) {
            break
        }
        builder.append(child.text)
    }
    return builder.toString()
}
