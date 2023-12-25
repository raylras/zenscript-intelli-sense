package raylras.zen.lsp.provider.data

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.messages.Either
import raylras.zen.model.CompilationEnvironment
import raylras.zen.model.parser.ZenScriptParser.MemberAccessExprContext
import raylras.zen.model.symbol.Operator
import raylras.zen.model.symbol.applyUnaryOperator
import raylras.zen.model.type.ListType
import raylras.zen.model.type.MapType
import raylras.zen.model.type.Type
import raylras.zen.util.textRange
import raylras.zen.util.toLspRange

object Snippet {
    fun dotFor(type: Type, env: CompilationEnvironment, ctx: MemberAccessExprContext): CompletionItem? {
        return when (type.applyUnaryOperator(Operator.FOR_IN, env)) {
            is MapType -> {
                createMemberAccessCompletion(
                    "for",
                    "for key, value in map",
                    "for \${1:key}, \${2:value} in %s {\n\t$0\n}",
                    ctx
                )
            }

            is ListType -> {
                createMemberAccessCompletion(
                    "for",
                    "for element in list",
                    "for \${1:value} in %s {\n\t$0\n}",
                    ctx
                )
            }

            else -> null
        }
    }

    fun dotForI(type: Type, env: CompilationEnvironment, ctx: MemberAccessExprContext): CompletionItem? {
        return when (type.applyUnaryOperator(Operator.FOR_IN, env)) {
            is ListType -> {
                createMemberAccessCompletion(
                    "fori",
                    "for index, element in list",
                    "for \${1:i}, \${2:value} in %s {\n\t$0\n}",
                    ctx
                )
            }

            else -> null
        }
    }

    fun dotVal(ctx: MemberAccessExprContext): CompletionItem {
        return createMemberAccessCompletion("val", "val name = expr", "val \${1:value} = %s;", ctx)
    }

    fun dotVar(ctx: MemberAccessExprContext): CompletionItem {
        return createMemberAccessCompletion("var", "var name = expr", "var \${1:value} = %s;", ctx)
    }

    fun dotIfNull(type: Type, ctx: MemberAccessExprContext): CompletionItem? {
        return if (type.isNullable()) {
            createMemberAccessCompletion(
                "null",
                "if (isNull(expr))",
                "if (isNull(%s)) {\n\t$0\n}",
                ctx
            )
        } else {
            null
        }
    }

    fun dotIfNotNull(type: Type, ctx: MemberAccessExprContext): CompletionItem? {
        return if (type.isNullable()) {
            createMemberAccessCompletion(
                "nn",
                "if (!isNull(expr))",
                "if (!isNull(%s)) {\n\t$0\n}",
                ctx
            )
        } else {
            null
        }
    }

    private fun createMemberAccessCompletion(
        name: String,
        description: String,
        snippet: String,
        ctx: MemberAccessExprContext
    ): CompletionItem {
        val item = CompletionItem(name)
        item.insertTextMode = InsertTextMode.AdjustIndentation
        item.insertTextFormat = InsertTextFormat.Snippet
        val labelDetails = CompletionItemLabelDetails()
        labelDetails.description = description
        item.labelDetails = labelDetails
        val textEdit = TextEdit(ctx.textRange.toLspRange(), snippet.format(ctx.expression().text))
        item.textEdit = Either.forLeft(textEdit)
        item.sortText = name
        item.filterText = ctx.expression().text + "." + name
        return item
    }
}
