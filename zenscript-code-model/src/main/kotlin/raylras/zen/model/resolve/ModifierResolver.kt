package raylras.zen.model.resolve

import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.RuleNode
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser.*
import raylras.zen.model.symbol.Modifiable.Modifier

fun getModifier(cst: ParseTree?): Modifier {
    return cst?.accept(ModifierVisitor) ?: Modifier.ERROR
}

private object ModifierVisitor : Visitor<Modifier>() {
    override fun visitFunctionDeclaration(ctx: FunctionDeclarationContext): Modifier? {
        return when {
            ctx.prefix != null -> {
                toModifier(ctx.prefix)
            }

            isToplevel(ctx) -> {
                Modifier.IMPLICIT_STATIC
            }

            else -> null
        }
    }

    override fun visitVariableDeclaration(ctx: VariableDeclarationContext): Modifier? {
        return toModifier(ctx.prefix)
    }

    override fun visitForeachVariable(ctx: ForeachVariableContext): Modifier {
        return Modifier.IMPLICIT_VAR
    }

    override fun visitChildren(node: RuleNode?): Modifier? {
        return null
    }

    private fun toModifier(token: Token): Modifier? {
        return when (token.type) {
            VAR -> Modifier.VAR
            VAL -> Modifier.VAL
            STATIC -> Modifier.STATIC
            GLOBAL -> Modifier.GLOBAL
            else -> null
        }
    }

    private fun isToplevel(cst: ParseTree): Boolean {
        return cst.parent is TopLevelElementContext
    }
}
