import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Ireena on 3/24/14.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node header, trailer ;
    private int size ;
    private Node head ;

    public Deque() {
        header = new Node(null, null, null);
        trailer = new Node(null, null, null);
        header.setNext(trailer);
        trailer.setPrevious(header);

    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size ;
    }
    public void addFirst(Item item){
        if(item == null){
            throw new NullPointerException();
        }
        Node currentFirst = header.getNext();
        Node newNode = new Node(item, currentFirst,header);
        header.setNext(newNode);
        currentFirst.setPrevious(newNode);
        size++;
    }

    public void addLast(Item item){
        if(item == null){
            throw new NullPointerException();
        }
        Node currentLast = trailer.getPrevious();
        Node newNode = new Node(item, trailer, currentLast );
        trailer.setPrevious(newNode);
        currentLast.setNext(newNode);
        size++;
    }

    public Item removeFirst(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        Node node = header.getNext();
        header.setNext(node.getNext());
        node.getNext().setPrevious(header);
        size--;
        return node.getItem() ;

    }
    public Item removeLast(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        Node node = trailer.getPrevious();
        trailer.setPrevious(node.getPrevious());
        node.getPrevious().setNext(trailer);
        size--;
        return node.getItem();
    }


    public static void main(String[] args) {

    }
     private class Node {
        private Item item ;
        private Node next ;
        private Node previous ;

        Node(Item item, Node next, Node previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }
    private class DequeIterator<T> implements Iterator<T>{
        Node currNode = header.getNext();
        public boolean hasNext() {
            return (currNode != trailer);
        }

        public T next() {
            if(currNode == trailer){
                throw new NoSuchElementException();
            }
            T item = (T)currNode.getItem() ;
            currNode = currNode.getNext();
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>();
    }
}
