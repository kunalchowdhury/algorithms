package com.study.parser.collection;

/**
 * Created by Kunal Chowdhury on 5/9/2015.
 */
public class ParserStateTransition {
    public static final char OPENING_BRACKET = '(';
    public static final char OR_OPERATOR = '|';
    public static final char CLOSING_BRACKET = ')';
    public static final char WILDCARD = '*';
    private String regex;
    private int regexLength;
    private StringBuilder[] m ;
    private ObjectGraph objGraph;


    public ParserStateTransition(String regex) {
        this.regex = regex;
        regexLength = regex.length();
        this.m = new StringBuilder[regexLength];
        Stack<Integer> operators = new Stack<>();
        objGraph = new ObjectGraph(regexLength +1);
        for (int i = 0; i < regexLength; i++) {
            int point = i;
            if (regex.charAt(i) == OPENING_BRACKET || regex.charAt(i) == OR_OPERATOR)
                operators.push(i);
            else if (regex.charAt(i) == CLOSING_BRACKET) {
                int orpoint = operators.pop();
                if (regex.charAt(orpoint) == OR_OPERATOR) {
                    point = operators.pop();
                    objGraph.addEdge(point, orpoint + 1);
                    objGraph.addEdge(orpoint, i);
                }
                else if (regex.charAt(orpoint) == OPENING_BRACKET)
                    point = orpoint;

            }

           if (i < regexLength -1 && regex.charAt(i+1) == WILDCARD) {
                objGraph.addEdge(point, i + 1);
                objGraph.addEdge(i + 1, point);
            }
            if (regex.charAt(i) == OPENING_BRACKET ||
                    regex.charAt(i) == WILDCARD ||
                    regex.charAt(i) == CLOSING_BRACKET)
                objGraph.addEdge(i, i+1);
        }
    }

    public boolean recognizes(String txt) {
        boolean[] visited = objGraph.performDepthFirst(0);
        List<Integer> pc = new List<>();
        for (int v = 0; v < objGraph.vertices(); v++)
            if (visited[v]) pc.add(v);

        for (int i = 0; i < txt.length();i++ ) {
            List<Integer> match = new List<>();
            boolean charMatched = false;
            for (int v : pc) {
                if (v == regexLength) continue;
                if ((regex.charAt(v) == txt.charAt(i)) || regex.charAt(v) == '.') {
                    match.add(v + 1);
                    charMatched = true;
                }
                if(regex.charAt(v) == '^'){
                    boolean isAlpha = ((65 <= txt.charAt(i)) && (txt.charAt(i) <= 90)) ||
                            ((97 <= txt.charAt(i)) && (txt.charAt(i) <= 122)) || txt.charAt(i) == ';';
                    if (isAlpha)  {
                        match.add(v+1);
                        charMatched = true;
                    }
                }
                if(regex.charAt(v) == '%'){
                    boolean isSpace = txt.charAt(i) == ' ';
                    if(isSpace){
                        match.add(v+1);
                        charMatched = true;
                    }
                }
                if(regex.charAt(v) == '#'){
                    boolean numeric =  48 <= txt.charAt(i) && txt.charAt(i) <= 57 ;
                    if(numeric){
                        match.add(v+1);
                        charMatched = true;
                    }
                }
                if(charMatched ) {
                    if (m[v] != null) {
                        m[v].append(txt.charAt(i));
                    }else {
                        m[v] = new StringBuilder().append(txt.charAt(i));
                    }
                }

            }
            boolean[] newTraversal = objGraph.performDepthFirst(match);
            pc = new List<>();
            for (int v = 0; v < objGraph.vertices(); v++)
                if (newTraversal[v]) {
                    pc.add(v);
                }
            if (pc.size() == 0) return false;
        }

        for (int v : pc)
            if (v == regexLength) return true;
        return false;
    }

    public static void main(String[] args) {
        String regexp = "(" + args[0] + ")";
        String txt = args[1];

        System.out.println("Does "+regexp+" match "+txt + " ?");
        long l1 = System.currentTimeMillis();
        ParserStateTransition nfa = new ParserStateTransition(regexp);

        if(nfa.recognizes(txt)){
            System.out.println("with new code -- " + nfa.m[3] + "-- " + nfa.m[8]);
        }

        System.out.println(nfa.recognizes(txt));

        long l2 = System.currentTimeMillis();

        System.out.println(l2-l1);

        /*long l3 = System.currentTimeMillis();
        Pattern p = Pattern.compile("(a|aa)*b");

        Matcher m = p.matcher("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaac");
        System.out.println(m.matches());
        long l4 = System.currentTimeMillis();

        System.out.println(l4-l3);*/

    }

}
