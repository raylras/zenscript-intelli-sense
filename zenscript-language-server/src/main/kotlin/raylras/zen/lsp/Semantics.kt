package raylras.zen.lsp

import org.eclipse.lsp4j.SemanticTokenModifiers
import org.eclipse.lsp4j.SemanticTokenTypes
import org.eclipse.lsp4j.SemanticTokensLegend
import raylras.zen.model.symbol.Modifiable
import raylras.zen.model.symbol.Modifiable.Modifier.*
import java.util.*
import java.util.stream.Collectors

object Semantics {
    val SEMANTIC_TOKENS_LEGEND: SemanticTokensLegend

    init {
        val tokenTypes = Arrays.stream(TokenType.entries.toTypedArray()).map { tokenType: TokenType -> tokenType.tokenName }
            .collect(Collectors.toList())
        val tokenModifiers = Arrays.stream(TokenModifier.entries.toTypedArray())
            .map { tokenModifier: TokenModifier -> tokenModifier.modifierName }
            .collect(Collectors.toList())
        SEMANTIC_TOKENS_LEGEND = SemanticTokensLegend(tokenTypes, tokenModifiers)
    }

    fun getTokenModifiers(modifier: Modifiable.Modifier?): Int {
        var tokenModifiers = 0
        when (modifier) {
            STATIC, GLOBAL -> {
                tokenModifiers = tokenModifiers or TokenModifier.STATIC.flag
                tokenModifiers = tokenModifiers or TokenModifier.READONLY.flag
                tokenModifiers = tokenModifiers or TokenModifier.DEFINITION.flag
            }

            VAL -> {
                tokenModifiers = tokenModifiers or TokenModifier.READONLY.flag
                tokenModifiers = tokenModifiers or TokenModifier.DEFINITION.flag
            }

            VAR, ERROR -> tokenModifiers =
                tokenModifiers or TokenModifier.DEFINITION.flag

            else -> null
        }
        return tokenModifiers
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

    enum class TokenModifier(val flag: Int, val modifierName: String) {
        DEFINITION(1, SemanticTokenModifiers.Definition),
        READONLY(2, SemanticTokenModifiers.Readonly),
        STATIC(4, SemanticTokenModifiers.Static)
    }
}
