package raylras.zen.util

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.TokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode
import kotlin.reflect.KClass

fun getCstAtPosition(root: ParseTree?, pos: TextPosition): ParseTree? {
    if (root == null) return null
    val deque = getCstStackAtPosition(root, pos)
    return deque.firstOrNull()
}

fun getCstStackAtPosition(root: ParseTree?, pos: TextPosition): ArrayDeque<ParseTree> {
    if (root == null) return ArrayDeque()
    val tempQueue = ArrayDeque<ParseTree>().apply { addLast(root) }
    val result = ArrayDeque<ParseTree>()
    while (tempQueue.isNotEmpty()) {
        val current = tempQueue.removeFirstOrNull() ?: continue
        if (pos in current.textRange) {
            result.addFirst(current)
            tempQueue.clear()
            for (i in 0 until current.childCount) {
                tempQueue.addLast(current.getChild(i))
            }
        }
    }
    return result
}

fun getPrevTerminal(tokenStream: TokenStream?, node: ParseTree?): TerminalNode? {
    if (tokenStream == null || node == null) return null
    val prevToken = getPrevToken(tokenStream, node) ?: return null
    val root = getRoot(node) ?: return null
    val range: TextRange = prevToken.textRange
    val prevNode = getCstAtPosition(root, range.end)
    return if (prevNode is TerminalNode) prevNode else null
}

fun getPrevToken(tokenStream: TokenStream, node: ParseTree?): Token? {
    var i = getStartTokenIndex(node) - 1
    while (i >= 0) {
        val token = tokenStream[i]
        if (token.channel == Token.DEFAULT_CHANNEL) {
            return token
        }
        i--
    }
    return null
}

fun findParentOfTypes(cst: ParseTree?, vararg parents: KClass<*>): ParseTree? {
    var current = cst
    while (current != null) {
        for (parent in parents) {
            if (parent.isInstance(current)) {
                return current
            }
        }
        current = current.parent
    }
    return null
}

private fun getStartTokenIndex(node: ParseTree?): Int {
    return when (node) {
        is TerminalNode -> {
            getTokenIndex(node.symbol)
        }

        is ParserRuleContext -> {
            getTokenIndex(node.start)
        }

        else -> -1
    }
}

private fun getStopTokenIndex(node: ParseTree?): Int {
    return when (node) {
        is TerminalNode -> {
            getTokenIndex(node.symbol)
        }

        is ParserRuleContext -> {
            getTokenIndex(node.stop)
        }

        else -> -1
    }
}

private fun getTokenIndex(token: Token?): Int {
    return token?.tokenIndex ?: -1
}

private fun getRoot(node: ParseTree?): ParseTree? {
    var root: ParseTree? = node
    while (root != null && root.parent != null) {
        root = root.parent
    }
    return root
}
