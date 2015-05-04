package com.study.parser.collection;

/**
 * Created by Kunal Chowdhury on 5/3/2015.
 */
public class LookupTable {

    private List[] lists ;
    private int count ;

    LookupTable(int count) {
        this.count = count;
        this.lists = new List[count];
    }

    void add(int id, List l){
        lists[id] = l ;
    }

    List get(int id){
        return lists[id];
    }
}
