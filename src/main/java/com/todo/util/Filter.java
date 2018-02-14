package com.todo.util;

public class Filter {
    private String field;
    private String value;
    private Verb  verb;

    public Filter(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

}
