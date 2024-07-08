package raylras.intellizen.util

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.TerminalNode
import org.antlr.v4.runtime.tree.Tree

/**
 * TextRange in a text document expressed as zero-based start and end positions.
 *
 * @see TextPosition
 */
data class TextRange(val start: TextPosition, val end: TextPosition) {
    constructor(startLine: Int, startColumn: Int, endLine: Int, endColumn: Int) :
        this(TextPosition(startLine, startColumn), TextPosition(endLine, endColumn))

    override fun toString(): String {
        return "(" + start.line + ":" + start.column + ")-(" + end.line + ":" + end.column + ')'
    }
}

operator fun TextRange?.contains(that: TextRange?): Boolean {
    if (this == null || that == null) {
        return false
    }
    if (this.start.line > that.start.line || this.end.line < that.end.line) {
        return false
    }
    if (this.start.line == that.start.line && this.start.column > that.start.column) {
        return false
    }
    if (this.end.line == that.end.line && this.end.column < that.end.column) {
        return false
    }
    return true
}

operator fun TextRange?.contains(pos: TextPosition?): Boolean {
    if (this == null || pos == null) {
        return false
    }
    return this.contains(TextRange(pos, pos))
}

val NO_RANGE = TextRange(NO_POSITION, NO_POSITION)

val Tree.textRange: TextRange
    get() {
        return when (this) {
            is TerminalNode -> {
                this.symbol.textRange
            }

            is ParserRuleContext -> {
                val (start, _) = this.start?.textRange ?: NO_RANGE
                val (_, end) = this.stop?.textRange ?: NO_RANGE
                TextRange(start.line, start.column, end.line, end.column)
            }

            else -> NO_RANGE
        }
    }

val Token.textRange: TextRange
    get() {
        val startLine = this.line - ANTLR_BASE_LINE
        val startColumn = this.charPositionInLine
        val endColumn = startColumn + this.text.length
        return TextRange(startLine, startColumn, startLine, endColumn)
    }

operator fun Tree?.contains(that: Tree?): Boolean {
    if (this == null || that == null) return false
    return this.textRange.contains(that.textRange)
}

operator fun Tree?.contains(token: Token?): Boolean {
    if (this == null || token == null) return false
    return this.textRange.contains(token.textRange)
}

operator fun Token?.contains(that: Token?): Boolean {
    if (this == null || that == null) return false
    return this.textRange.contains(that.textRange)
}

operator fun Token?.contains(tree: Tree?): Boolean {
    if (this == null || tree == null) return false
    return this.textRange.contains(tree.textRange)
}
