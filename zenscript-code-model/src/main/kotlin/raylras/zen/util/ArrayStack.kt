package raylras.zen.util

class ArrayStack<E> : Stack<E> {
    private val deque = ArrayDeque<E>()

    override fun push(e: E) = deque.addFirst(e)

    override fun pop(): E? = deque.removeFirstOrNull()

    override fun peek(): E? = deque.firstOrNull()

    override fun size(): Int = deque.size

    override fun isEmpty(): Boolean = deque.isEmpty()

    override fun isNotEmpty(): Boolean = deque.isNotEmpty()

    override fun clear() = deque.clear()

    override fun iterator(): Iterator<E> = deque.iterator()
}
