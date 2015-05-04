package com.study.parser.collection;

import java.util.Iterator;

/**
 * Created by Kunal Chowdhury on 5/3/2015.
 */
public class List<T> implements Iterable<T> {

    private int count;
    private Node<T> first;

    public List() {
        first = null;
        count = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return count;
    }

    public void add(T item) {
        Node<T> oldfirst = first;
        first = new Node<T>();
        first.val = item;
        first.next = oldfirst;
        count++;
    }

    public Iterator<T> iterator()  {
        return new ListIterator<T>(first);
    }


}

