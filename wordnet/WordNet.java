import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ireena on 5/14/14.
 */
public class WordNet {
    private ST<Integer, Set <String>> synsetMap ;

    private ST<String, Set<Integer>> nounIdMap ;

    private SET<String> nouns ;

    private Digraph digraph ;

    private SAP sap ;

    public WordNet(String synsets, String hypernyms){
        In in = new In(synsets);
        String s ;

        synsetMap = new ST<Integer, Set<String>>();
        nounIdMap = new ST<String, Set<Integer>>();
        nouns = new SET<String>();

        while((s=in.readLine()) != null){
            String[] firstSplit = s.split(",");
            Integer id = Integer.parseInt(firstSplit[0].trim());
            synsetMap.put(id, new HashSet<String>());
            for(String n : firstSplit[1].split("\\s+")){
                   synsetMap.get(id).add(n.trim());
                   if(!nounIdMap.contains(n.trim())){
                       nounIdMap.put(n.trim(), new HashSet<Integer>());
                   }
                   nounIdMap.get(n.trim()).add(id);
                   nouns.add(n.trim());
            }
        }

        in = new In(hypernyms);
        digraph = new Digraph(synsetMap.size());

        while((s=in.readLine()) != null){
            String[] str = s.split(",");
            for(int i= 1 ; i < str.length ; i++){
                 digraph.addEdge(Integer.parseInt(str[0]),Integer.parseInt(str[i]));
            }

        }
        sap = new SAP(digraph);

    }

    public Iterable<String> nouns(){
        return nouns;
    }

    public boolean isNoun(String word){
        return nouns.contains(word);
    }

    public int distance(String nounA, String nounB){
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        Set<Integer> nounASet = new HashSet<Integer>();
        Set<Integer> nounBSet = new HashSet<Integer>();

        Set<Integer> ss = nounIdMap.get(nounA);
        nounASet.addAll(ss);
        for(Integer i : ss){
           for(String s : synsetMap.get(i)){
                   nounASet.addAll(nounIdMap.get(s));
           }
        }

        ss = nounIdMap.get(nounB);
        nounBSet.addAll(ss);
        for(Integer i : ss){
            for(String s : synsetMap.get(i)){
                nounBSet.addAll(nounIdMap.get(s));
            }
        }



       // System.out.println("A "+nounASet+" B "+nounBSet);
        return sap.length(nounASet, nounBSet);
    }

    public String sap(String nounA, String nounB){
        if(!isNoun(nounA) || !isNoun(nounB)){
            throw new IllegalArgumentException();
        }

       /* SET<Integer> nounASet = new SET<Integer>();
        SET<Integer> nounBSet = new SET<Integer>();*/

       /* for(String s : synsetMap.get(nounIdMap.get(nounA))){
            nounASet.add(nounIdMap.get(s));
        }

        for(String s : synsetMap.get(nounIdMap.get(nounB))){
            nounBSet.add(nounIdMap.get(s));
        }*/

        Set<Integer> nounASet = new HashSet<Integer>();
        Set<Integer> nounBSet = new HashSet<Integer>();

        Set<Integer> ss = nounIdMap.get(nounA);
        for(Integer i : ss){
            for(String s : synsetMap.get(i)){
                nounASet.addAll(nounIdMap.get(s));
            }
        }

        ss = nounIdMap.get(nounB);
        for(Integer i : ss){
            for(String s : synsetMap.get(i)){
                nounBSet.addAll(nounIdMap.get(s));
            }
        }

        int index = sap.ancestor(nounASet, nounBSet);
        return synsetMap.get(index).toArray(new String[0])[0];


    }

    public static void main(String[] args) {
         WordNet w = new WordNet("E:\\code\\src\\synsets.txt", "E:\\code\\src\\hypernyms.txt");
         System.out.println(w.distance("horse",  "horse"));
    }

}
