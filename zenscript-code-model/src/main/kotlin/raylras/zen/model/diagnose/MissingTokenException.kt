package raylras.zen.model.diagnose

import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.RecognitionException

class MissingTokenException(recognizer: Parser) : RecognitionException(recognizer, recognizer.inputStream, recognizer.context) {

    init {
        offendingToken = recognizer.currentToken
    }

}

