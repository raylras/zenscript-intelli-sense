package raylras.zen.lsp.provider.data

import org.eclipse.lsp4j.SemanticTokenModifiers
import org.eclipse.lsp4j.SemanticTokenTypes
import org.eclipse.lsp4j.SemanticTokensLegend
import raylras.zen.model.symbol.*

val SEMANTIC_TOKENS_LEGEND = SemanticTokensLegend().apply {
    tokenTypes = TokenType.entries.map { it.tokenName }
    tokenModifiers = TokenModifier.entries.map { it.modifierName }
}

enum class TokenType(val tokenName: String) {
    TYPE(SemanticTokenTypes.Type),
    CLASS(SemanticTokenTypes.Class),
    PARAMETER(SemanticTokenTypes.Parameter),
    VARIABLE(SemanticTokenTypes.Variable),
    FUNCTION(SemanticTokenTypes.Function),
    KEYWORD(SemanticTokenTypes.Keyword),
    MODIFIER(SemanticTokenTypes.Modifier),
    COMMENT(SemanticTokenTypes.Comment),
    STRING(SemanticTokenTypes.String),
    NUMBER(SemanticTokenTypes.Number),
    OPERATOR(SemanticTokenTypes.Operator)
}

enum class TokenModifier(val bitflag: Int, val modifierName: String) {
    DECLARATION(0b1, SemanticTokenModifiers.Declaration),
    READONLY(0b10, SemanticTokenModifiers.Readonly),
    STATIC(0b100, SemanticTokenModifiers.Static),
    GLOBAL(0b1000, "global")
}

val Modifiable.tokenModifier: Int
    get() = when {
        this.isGlobal -> {
            TokenModifier.GLOBAL.bitflag + TokenModifier.READONLY.bitflag
        }

        this.isStatic -> {
            TokenModifier.STATIC.bitflag + TokenModifier.READONLY.bitflag
        }

        this.isReadonly -> {
            TokenModifier.READONLY.bitflag
        }

        else -> {
            0
        }
    }

val Symbol.tokenType: TokenType?
    get() = when (this) {
        is VariableSymbol -> {
            TokenType.VARIABLE
        }

        is ParameterSymbol -> {
            // Workaround: in VSCode, it seems that read-only Parameter are not highlighted as expected, but Variable working fine.
            TokenType.VARIABLE
        }

        is ClassSymbol -> {
            TokenType.CLASS
        }

        is FunctionSymbol -> {
            TokenType.FUNCTION
        }

        is ConstructorSymbol -> {
            TokenType.FUNCTION
        }

        is OperatorFunctionSymbol -> {
            TokenType.FUNCTION
        }

        is ExpandFunctionSymbol -> {
            TokenType.FUNCTION
        }

        else -> {
            null
        }
    }
