package raylras.zen.model.diagnose

import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.misc.IntervalSet
import raylras.zen.util.l10n.L10N

class PrettyErrorStrategy: DefaultErrorStrategy() {

    override fun reportNoViableAlternative(recognizer: Parser, e: NoViableAltException) {
//        val tokens = recognizer.inputStream
//        val input: String
//        input = if (tokens != null) {
//            if (e.startToken.type == Token.EOF) "<EOF>" else tokens.getText(e.startToken, e.offendingToken)
//        } else {
//            "<unknown input>"
//        }
        val msg = L10N.localize("error_illegal_input")
        recognizer.notifyErrorListeners(e.offendingToken, msg, e)
    }

    override fun reportInputMismatch(recognizer: Parser, e: InputMismatchException) {

        val tokenName = getTokenErrorDisplay(e.offendingToken)
        val expecting = getUserExpectedTokens(recognizer)

        val msg = if(expecting.size() == 0) {
            L10N.localize("error_illegal_input_expecting",tokenName, expecting.toString(recognizer.vocabulary))
        } else {
            L10N.localize("error_illegal_input",tokenName)
        }
        recognizer.notifyErrorListeners(e.offendingToken, msg, e)
    }

    override fun reportFailedPredicate(recognizer: Parser, e: FailedPredicateException) {
        val ruleName = recognizer.ruleNames[recognizer.context.ruleIndex]
        val msg = "failed to predicate rule for " + ruleName + " " + e.message
        recognizer.notifyErrorListeners(e.offendingToken, msg, e)

    }


    override fun reportUnwantedToken(recognizer: Parser) {
        if (inErrorRecoveryMode(recognizer)) {
            return
        }

        beginErrorCondition(recognizer)

        val t = recognizer.currentToken
        val tokenName = getTokenErrorDisplay(t)
        val expecting = getUserExpectedTokens(recognizer)
        val msg = if(expecting.size() == 0) {
            L10N.localize("error_unexpected_token_expecting", tokenName, expecting.toString(recognizer.vocabulary))
        } else {
            L10N.localize("error_unexpected_token", tokenName)
        }
        recognizer.notifyErrorListeners(t, msg, null)
    }

    override fun reportMissingToken(recognizer: Parser) {

        if (inErrorRecoveryMode(recognizer)) {
            return
        }
        beginErrorCondition(recognizer)

        val t = recognizer.currentToken

        val expecting = getUserExpectedTokens(recognizer)
        val msg = L10N.localize("error_missing_token", expecting.toString(recognizer.vocabulary))

        recognizer.notifyErrorListeners(t, msg, MissingTokenException(recognizer))
    }

    private fun getUserExpectedTokens(recognizer: Parser): IntervalSet {
        // TODO: better display expected tokens+
        return recognizer.expectedTokens
//        if(autoToken.size() < 2) {
//            return autoToken
//        }
//
//
//        val visitor = ExpectedTokenVisitor()
//        recognizer.context.accept(visitor)
//        return visitor.result
    }
}