package com.todo.util;

public class Page {
    private int size;
    private int page;

    public Page(int size, int page) {
        this.size = size;
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public int getOffset(){
        return (page - 1) * getSize();
    }

}
