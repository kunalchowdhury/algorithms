package com.study.parser.collection;

import java.util.*;

/**
 * Created by Kunal Chowdhury on 5/10/2015.
 */
public class Stack<T> implements Iterable<T> {
    private Node<T> first;
    private int count;

    public Stack() {
        first = null;
        count = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return count;
    }

    public void push(T val) {
        Node<T> oldfirst = first;
        first = new Node<T>();
        first.val = val;
        first.next = oldfirst;
        count++;
    }

    public T pop() {
        if (isEmpty()) throw new RuntimeException("Empty stack !!");
        T val = first.val;
        first = first.next;
        count--;
        return val;
    }

    public T peek() {
        if (isEmpty()) throw new RuntimeException("Empty stack !!");
        return first.val;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<T>(first);
    }
}
