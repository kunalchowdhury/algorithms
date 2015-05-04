package com.study.parser.collection;

import java.util.Iterator;

/**
 * Created by Kunal Chowdhury on 5/3/2015.
 */
class ListIterator<Item> implements Iterator<Item> {
    private Node<Item> current;

    public ListIterator(Node<Item> first) {
        current = first;
    }

    public boolean hasNext()  { return current != null;                     }
    public void remove()      { throw new UnsupportedOperationException();  }

    public Item next() {
        Item item = current.val;
        current = current.next;
        return item;
    }
}