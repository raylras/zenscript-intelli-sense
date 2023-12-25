package raylras.zen.util

import org.antlr.v4.runtime.RuleContext
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode
import raylras.zen.model.parser.ZenScriptLexer
import raylras.zen.model.parser.ZenScriptParser

object DebugUtils {
    fun prettyPrintTree(node: ParseTree?): String {
        val builder = StringBuilder()
        prettyPrintTree(builder, "", node, true)
        return builder.toString()
    }

    private fun prettyPrintTree(builder: StringBuilder, indent: String, node: ParseTree?, last: Boolean) {
        if (node == null) {
            return
        }

        val text: String?
        if (node is TerminalNode) {
            val token = node.symbol
            val tokenType = token.type
            text = if (tokenType == -1) {
                "<EOF>"
            } else {
                ZenScriptLexer.ruleNames[tokenType - 1] + ":" + token.text
            }
        } else if (node is RuleContext) {
            val ruleIndex = node.ruleIndex
            text = ZenScriptParser.ruleNames[ruleIndex]
        } else {
            text = null
        }

        builder.append(indent).append(if (last) "\\-- " else "|-- ").append(text).append("\n")

        val childCount = node.childCount
        for (i in 0 until childCount) {
            prettyPrintTree(builder, indent + (if (last) "    " else "|   "), node.getChild(i), i == childCount - 1)
        }
    }
}
