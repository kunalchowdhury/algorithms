/**
 * Created by Ireena on 5/17/14.
 */
public class Outcast {

    private WordNet wordNet;

    public Outcast(WordNet wordnet){
         this.wordNet = wordnet;
    }

    public String outcast(String[] nouns){
        String target = null;
        int len = Integer.MIN_VALUE;
        ST<String, Integer> s = new ST<String, Integer>();
        for(String str : nouns){
            s.put(str, 0);
            for(String n : nouns){
               s.put(str, s.get(str) + wordNet.distance(str, n));
            }

        }
        for(String k : s.keys()){
            if(s.get(k) > len){
                target = k;
                len = s.get(k);
            }
        }

        return target;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
