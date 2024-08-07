package raylras.intellizen.languageserver.provider.data

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import raylras.intellizen.CompilationEnvironment
import raylras.intellizen.languageserver.util.toLspRange
import raylras.intellizen.parser.ZenScriptParser.MemberAccessExprContext
import raylras.intellizen.symbol.Operator
import raylras.intellizen.symbol.applyUnaryOperator
import raylras.intellizen.type.ListType
import raylras.intellizen.type.MapType
import raylras.intellizen.type.Type
import raylras.intellizen.util.textRange

object Snippets {
    fun dotFor(type: Type, env: CompilationEnvironment, ctx: MemberAccessExprContext): CompletionItem? {
        val expr = ctx.expression().text
        return when (type.applyUnaryOperator(Operator.FOR_IN, env)) {
            is MapType -> {
                createMemberAccessCompletionItem(
                    "for",
                    "for key, value in map",
                    "for \${1:key}, \${2:value} in $expr {\n\t$0\n}",
                    ctx
                )
            }

            is ListType -> {
                createMemberAccessCompletionItem(
                    "for",
                    "for element in list",
                    "for \${1:value} in $expr {\n\t$0\n}",
                    ctx
                )
            }

            else -> null
        }
    }

    fun dotForI(type: Type, env: CompilationEnvironment, ctx: MemberAccessExprContext): CompletionItem? {
        val expr = ctx.expression().text
        return when (type.applyUnaryOperator(Operator.FOR_IN, env)) {
            is ListType -> {
                createMemberAccessCompletionItem(
                    "fori",
                    "for index, element in list",
                    "for \${1:i}, \${2:value} in $expr {\n\t$0\n}",
                    ctx
                )
            }

            else -> null
        }
    }

    fun dotVal(ctx: MemberAccessExprContext): CompletionItem {
        val expr = ctx.expression().text
        return createMemberAccessCompletionItem("val", "val name = expr", "val \${1:value} = $expr;", ctx)
    }

    fun dotVar(ctx: MemberAccessExprContext): CompletionItem {
        val expr = ctx.expression().text
        return createMemberAccessCompletionItem("var", "var name = expr", "var \${1:value} = $expr;", ctx)
    }

    fun dotIfNull(type: Type, ctx: MemberAccessExprContext): CompletionItem? {
        val expr = ctx.expression().text
        if (type.isNullable()) {
            return createMemberAccessCompletionItem(
                "null",
                "if (isNull(expr))",
                "if (isNull(%$expr)) {\n\t$0\n}",
                ctx
            )
        }
        return null
    }

    fun dotIfNotNull(type: Type, ctx: MemberAccessExprContext): CompletionItem? {
        val expr = ctx.expression().text
        if (type.isNullable()) {
            return createMemberAccessCompletionItem(
                "nn",
                "if (!isNull(expr))",
                "if (!isNull($expr)) {\n\t$0\n}",
                ctx
            )
        }
        return null
    }

    private fun createMemberAccessCompletionItem(
        name: String,
        description: String,
        snippet: String,
        ctx: MemberAccessExprContext
    ): CompletionItem {
        return CompletionItem().apply {
            label = name
            kind = CompletionItemKind.Snippet
            insertTextMode = InsertTextMode.AdjustIndentation
            insertTextFormat = InsertTextFormat.Snippet
            labelDetails = CompletionItemLabelDetails().apply {
                this.description = description
            }
            textEdit = Either.forLeft(TextEdit().apply {
                range = ctx.textRange.toLspRange()
                newText = snippet
            })
            sortText = name
            filterText = ctx.expression().text + "." + name
        }
    }
}
