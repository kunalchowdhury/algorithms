package com.study.parser.collection;

import java.util.Iterator;

/**
 * Created by Kunal Chowdhury on 5/3/2015.
 */
public class LinkedQueue<T> implements Iterable<T> {

    private int elements;
    private Node<T> first;
    private Node<T> last;

    public LinkedQueue() {
        first = null;
        last  = null;
        elements = 0;
    }

    public int size() {
        return elements;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(T t) {
        Node<T> oldlast = last;
        last = new Node<T>();
        last.val = t;
        last.next = null;
        if (isEmpty()) first = last;
        else           oldlast.next = last;
        elements++;
    }

    public T dequeue() {
        T val = first.val;
        first = first.next;
        elements--;
        if (isEmpty()) last = null;
        return val;
    }

    public Iterator<T> iterator()  {
        return new ListIterator<T>(first);
    }
}
