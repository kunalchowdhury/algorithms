package com.study.parser.collection;

/**
 * Created by Kunal Chowdhury on 5/31/2015.
 */
public class ShuntingYard {
    enum Operation{
        PLUS('+', 2),MINUS('-' , 2),MUL('*', 3),DIV('/', 3), POW('^', 4);
        char c ;
        int precedence ;

        Operation(char c, int precedence) {
            this.c = c;
            this.precedence = precedence;
        }

        static boolean isOperation(char ch){
              for (Operation o : Operation.values()){
                if(o.c == ch) {
                    return true ;
                }
              }
              return false;
        }
        static int getPrecedence(char s){
            for (Operation o : Operation.values()){
                if(o.c == s) {
                    return o.precedence;
                }
            }
           // System.out.println(s);
            throw new RuntimeException("How did it reach here !!");
        }
    }


    public static String parse(String s){
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()){

            //System.out.println(c);
            if(c == ' ')continue;
            if(Character.isDigit(c)){
                 output.append(c);
            }else if (stack.size() == 0){
                stack.push(c);
            }else if (c == '('){
                stack.push(c);
            }else if (c ==  ')'){
                while(stack.peek() != '('){
                    output.append(stack.pop());
                }
                stack.pop();
            }else if(!Operation.isOperation(stack.peek())){
                stack.push(c);
            }
            else if(Operation.getPrecedence(c) > Operation.getPrecedence(stack.peek())){
                stack.push(c);
            }else if(c != '^' && Operation.getPrecedence(c) <= Operation.getPrecedence(stack.peek())){
                while (Operation.isOperation(stack.peek()) &&
                        Operation.getPrecedence(c) <= Operation.getPrecedence(stack.peek())){
                    output.append(stack.pop()) ;
                }
            }else if (c == '^' && Operation.getPrecedence(c) < Operation.getPrecedence(stack.peek())){
                while (Operation.isOperation(stack.peek()) &&
                        Operation.getPrecedence(c) < Operation.getPrecedence(stack.peek())){
                    output.append(stack.pop()) ;
                }
            } else if(c == '^' && (Operation.getPrecedence(c) == Operation.getPrecedence(stack.peek()))){
                    stack.push(c);
            }


        }

        for (Character c1 : stack){
            System.out.print("," + c1+",");
        }
        System.out.println("");
        while (stack.size() != 0){
            output.append(stack.pop());
        }
        return output.toString();
    }

    public static void main(String[] args) {
        System.out.println(parse("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3"));
    }
}
