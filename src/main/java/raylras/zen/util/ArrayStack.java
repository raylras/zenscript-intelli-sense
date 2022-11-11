package raylras.zen.util;

import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayStack<E> implements Stack<E> {

    private final Deque<E> deque;

    public ArrayStack() {
        this.deque = new ArrayDeque<>();
    }

    @Override
    public void push(E e) {
        deque.push(e);
    }

    @Override
    public E pop() {
        return deque.pop();
    }

    @Override
    public E peek() {
        return deque.peek();
    }

    @Override
    public int size() {
        return deque.size();
    }

    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    @Override
    public void clear() {
        deque.clear();
    }

}
