package raylras.zen.util

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.TokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.TerminalNode

fun ParseTree?.getTerminalAt(pos: TextPosition): TerminalNode? {
    return getCstStackAt(pos).firstOrNull() as? TerminalNode
}

fun ParseTree?.getCstStackAt(pos: TextPosition): List<ParseTree> {
    val result = ArrayDeque<ParseTree>()
    val tempQueue = mutableListOf(this)
    while (tempQueue.isNotEmpty()) {
        val current = tempQueue.removeFirst() ?: continue
        if (pos in current.textRange) {
            result.addFirst(current)
            tempQueue.clear()
            for (i in 0 until current.childCount) {
                tempQueue.add(current.getChild(i))
            }
        }
    }
    return result
}

fun ParseTree?.walkParent(): Sequence<ParseTree> {
    return generateSequence(this) { it.parent }
}

fun TerminalNode?.getPrev(tokenStream: TokenStream?): TerminalNode? {
    val prevToken = this?.startToken?.getPrev(tokenStream) ?: return null
    val prevNode = this.root.getTerminalAt(prevToken.textRange.end)
    return prevNode
}

fun Token?.getPrev(tokenStream: TokenStream?, channel: Int = Token.DEFAULT_CHANNEL): Token? {
    this ?: return null
    tokenStream ?: return null
    var i = this.tokenIndex.dec()
    while (i >= 0) {
        val token = tokenStream[i]
        if (token.channel == channel) {
            return token
        } else {
            i--
        }
    }
    return null
}

val ParseTree.startToken: Token?
    get() = when (this) {
        is TerminalNode -> {
            this.symbol
        }

        is ParserRuleContext -> {
            this.start
        }

        else -> null
    }

val ParseTree.stopToken: Token?
    get() = when (this) {
        is TerminalNode -> {
            this.symbol
        }

        is ParserRuleContext -> {
            this.stop
        }

        else -> null
    }

val ParseTree.root: ParseTree
    get() {
        var root = this
        while (root.parent != null) {
            root = root.parent
        }
        return root
    }
