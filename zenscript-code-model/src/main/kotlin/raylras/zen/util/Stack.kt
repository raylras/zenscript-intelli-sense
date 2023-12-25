package raylras.zen.util

// bad java stack and deque: http://baddotrobot.com/blog/2013/01/10/stack-vs-deque
interface Stack<E>: Iterable<E> {
    fun push(e: E)

    fun pop(): E?

    fun peek(): E?

    fun size(): Int

    fun isEmpty(): Boolean

    fun isNotEmpty(): Boolean

    fun clear()
}
