package com.study.parser.collection;

/**
 * Created by Kunal Chowdhury on 5/3/2015.
 */
public class FieldIndexer {

    private FieldWrapper fields[] ;

    public FieldIndexer(int size) {
        this.fields = new FieldWrapper[size];
    }

    public FieldWrapper field(int id){
        return fields[id];
    }
}
