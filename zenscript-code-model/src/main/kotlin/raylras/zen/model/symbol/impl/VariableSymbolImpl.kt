package raylras.zen.model.symbol.impl

import org.antlr.v4.runtime.ParserRuleContext
import raylras.zen.model.CompilationUnit
import raylras.zen.model.Visitor
import raylras.zen.model.parser.ZenScriptParser
import raylras.zen.model.parser.ZenScriptParser.ForeachVariableContext
import raylras.zen.model.parser.ZenScriptParser.VariableDeclarationContext
import raylras.zen.model.resolve.getType
import raylras.zen.model.symbol.Modifiable.Modifier
import raylras.zen.model.symbol.ParseTreeLocatable
import raylras.zen.model.symbol.VariableSymbol
import raylras.zen.model.type.Type
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

fun createVariableSymbol(
    simpleNameCtx: ZenScriptParser.SimpleNameContext?,
    ctx: ParserRuleContext,
    unit: CompilationUnit,
    callback: (VariableSymbol) -> Unit
) {
    simpleNameCtx ?: return
    callback(object : VariableSymbol, ParseTreeLocatable {
        override val simpleName: String
            get() = simpleNameCtx.text

        override val type: Type
            get() = getType(ctx, unit)

        override val modifier: Modifier
            get() = ctx.accept(modifierResolver)

        override val cst: ParserRuleContext
            get() = ctx

        override val unit: CompilationUnit
            get() = unit

        override val textRange: TextRange
            get() = ctx.textRange

        override val selectionTextRange: TextRange
            get() = simpleNameCtx.textRange

        override fun toString(): String {
            return simpleName
        }
    })
}

fun createVariableSymbol(simpleName: String, type: Type, modifier: Modifier): VariableSymbol {
    return object : VariableSymbol {
        override val simpleName = simpleName

        override val type = type

        override val modifier = modifier
    }
}

private val modifierResolver = object : Visitor<Modifier>() {
    override fun visitVariableDeclaration(ctx: VariableDeclarationContext): Modifier {
        return when (ctx.prefix.type) {
            ZenScriptParser.VAR -> Modifier.VAR
            ZenScriptParser.VAL -> Modifier.VAL
            ZenScriptParser.STATIC -> Modifier.STATIC
            ZenScriptParser.GLOBAL -> Modifier.GLOBAL
            else -> Modifier.ERROR
        }
    }

    override fun visitForeachVariable(ctx: ForeachVariableContext): Modifier {
        return Modifier.IMPLICIT_VAR
    }
}
