package raylras.zen.util;

// bad java stack and deque: http://baddotrobot.com/blog/2013/01/10/stack-vs-deque
public interface Stack<E> {

    void push(E e);

    E pop();

    E peek();

    int size();

    boolean isEmpty();

    void clear();

}
