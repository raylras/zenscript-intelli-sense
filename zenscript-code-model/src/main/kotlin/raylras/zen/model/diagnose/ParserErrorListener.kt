package raylras.zen.model.diagnose

import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.FailedPredicateException
import org.antlr.v4.runtime.NoViableAltException
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer
import org.antlr.v4.runtime.Token
import raylras.zen.util.ANTLR_BASE_LINE
import raylras.zen.util.TextRange
import raylras.zen.util.textRange

class ParserErrorListener(
    private val diagnoseHandler: DiagnoseHandler
) : BaseErrorListener() {
    override fun syntaxError(
        recognizer: Recognizer<*, *>,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        msg: String,
        e: RecognitionException?
    ) {
        val token = offendingSymbol as? Token ?: return
        val range = when (e) {
            is FailedPredicateException -> {
                // grammar problem
                diagnoseHandler.addSystemError(msg + ", at: " + token.textRange.toString())
                return
            }

            is NoViableAltException -> {
                val startToken = e.startToken
                val startLine = startToken.line - ANTLR_BASE_LINE
                val startColumn = startToken.charPositionInLine
                val endLine = token.line - ANTLR_BASE_LINE
                val endColumn = token.charPositionInLine + token.text.length
                TextRange(startLine, startColumn, endLine, endColumn)
            }

            is MissingTokenException -> {
                val tokenStream = recognizer.inputStream as? CommonTokenStream ?: return

                val prev = tokenStream.LT(-1)
                val prevLine = prev.line - ANTLR_BASE_LINE
                val prevColumn = prev.charPositionInLine + prev.text.length
                TextRange(prevLine, prevColumn, prevLine, prevColumn + 1)

            }

            else -> token.textRange
        }
        diagnoseHandler.addError(msg, range)
    }
}