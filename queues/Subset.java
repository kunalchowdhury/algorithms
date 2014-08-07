import java.util.Arrays;

/**
 * Created by Ireena on 3/25/14.
 */
public class Subset {
    public static void main(String[] args) {
       Deque<String> deque = new Deque<String>();
       int k = Integer.parseInt(args[0]) ;
       StringBuilder sb = new StringBuilder();
       while(!StdIn.isEmpty()){
             sb.append(StdIn.readString()).append(" ");
       }
       String[] strings = sb.toString().split("\\s+");

        for (int i = 1; i < k+1 ; i++) {
            int index = StdRandom.uniform(i,strings.length);
            String val = strings[index];
            strings[index] = strings[i];
            strings[i] = val;
            deque.addFirst(val);
        }
        for(String val : deque){
            System.out.println(val);
        }

    }

}
