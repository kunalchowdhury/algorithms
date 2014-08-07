import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Ireena on 3/25/14.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items ;

    private int size ;

    public RandomizedQueue(){
        items = (Item[])new Object[1];
    }
    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
    public void enqueue(Item item){
        if(item == null){
            throw new NullPointerException();
        }
        if(size == items.length){
            resize(2*items.length);
        }
        items[size++] = item;
    }
    public Item dequeue(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        Item item = items[index];
        items[index] = null;
        for (int i = index; i < size-1 ; i++) {
             items[i] = items[i+1];
        }
        size--;
        if(size == items.length/4){
             resize(items.length/2);
        }
        return item ;
    }
    public Item sample(){
        return items[StdRandom.uniform(size)];
    }

    private void resize(int i) {
        Item[] newArr = (Item[]) new Object[i];
        for (int j = 0; j < size ; j++) {
              newArr[j] = items[j] ;
        }
        items = newArr;
    }

    private class RandomizedQueueIterator<T> implements Iterator<T>{
        int curr ;
        T[] values ;

        private RandomizedQueueIterator() {
            this.values = (T[])items;
        }

        public boolean hasNext() {
            return curr != (size -1);
        }

        public T next() {
            System.out.println("s "+curr);
            if(curr == (size -1)){
                throw new NoSuchElementException();
            }
            int index = StdRandom.uniform(curr+1);
            T t = (T)values[curr];
            values[curr] = values[index];
            values[index] = t;
            curr++;
            return t;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>();
    }

    public static void main(String[] args) {
       int n = 0;


            RandomizedQueue<String> queue = new RandomizedQueue<String>();
            queue.enqueue("A");
            queue.enqueue("B");
            queue.enqueue("C");
            queue.enqueue("D");
            queue.enqueue("E");
            queue.enqueue("F");
            queue.enqueue("G");
            queue.enqueue("H");



       outer: while (n < 10000){
            Iterator<String> iterator = queue.iterator();
          //  System.out.println("run no "+n);
            String s = null;
            while(!"E".equals(s)){
                s = iterator.next();
                System.out.println(s);
             }
             n++;
            }
        }


}
