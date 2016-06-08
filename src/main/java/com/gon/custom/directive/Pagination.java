package com.gon.custom.directive;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private List<Integer> ret = new ArrayList<Integer>();

    private int size;

    private int step;

    private int page;
    
    public void setSize(int size) {
        this.size = size;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void add(int s, int f) {
        for (int i = s; i < f; i++) {
            ret.add(i);
        }
    }
    
    public Pagination(){
        
    }
    
    public Pagination(int size,int page,int step){
        this.size = size;
        this.page = page;
        this.step = step;
    }

    public void last() {
        ret.add(-1);
        ret.add(size);
    }

    public void first() {
        ret.add(1);
        ret.add(-1);
    }

    public List<Integer> start() {
        if (size <= step * 2 + 5) {
            add(1, size + 1);
        } else if (page <= step + 3) {
            add(1, step * 2 + 4);
            last();
        } else if (page >= size - step * 2 - 1) {
            first();
            add(size - step * 2 - 2, size + 1);
        } else {
            first();
            add(page - step, page + step + 1);
            last();
        }
        return ret;
    }
}