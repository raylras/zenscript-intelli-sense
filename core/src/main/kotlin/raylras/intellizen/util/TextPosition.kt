package raylras.intellizen.util

/**
 * TextPosition in a text document expressed as zero-based line and column.
 *
 * @see TextRange
 */
data class TextPosition(val line: Int, val column: Int) {
    override fun toString(): String {
        return "($line:$column)"
    }
}

const val BASE_LINE = 0
const val BASE_COLUMN = 0
const val MAX_LINE = Int.MAX_VALUE
const val MAX_COLUMN = Int.MAX_VALUE
const val NO_LINE = -1
const val NO_COLUMN = -1
val NO_POSITION = TextPosition(NO_LINE, NO_COLUMN)

const val ANTLR_BASE_LINE: Int = 1 // org.antlr.v4.runtime.Token.getLine()
const val ANTLR_BASE_COLUMN: Int = 0 // org.antlr.v4.runtime.Token.getCharPositionInLine()
const val LSP4J_BASE_LINE: Int = 0 // org.eclipse.lsp4j.Position.getLine()
const val LSP4J_BASE_COLUMN: Int = 0 // org.eclipse.lsp4j.Position.getCharacter()
