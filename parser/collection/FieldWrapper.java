package com.study.parser.collection;

/**
 * Created by Kunal Chowdhury on 5/3/2015.
 */
public class FieldWrapper<T extends Class<T>> {

    private String field ;
    private T aClass;
    private int id ; // id in the mapping xml


    FieldWrapper(String field, T aClass, int id) {
        this.field = field;
        this.aClass = aClass;
        this.id = id;
    }

    String getField() {
        return field;
    }

    T getaClass() {
        return aClass;
    }

    int getId() {
        return id;
    }
}
