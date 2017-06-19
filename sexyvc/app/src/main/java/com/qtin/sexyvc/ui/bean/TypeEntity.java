package com.qtin.sexyvc.ui.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/16.
 */
public class TypeEntity {

    private int total;
    private ArrayList<FilterEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<FilterEntity> getList() {
        return list;
    }

    public void setList(ArrayList<FilterEntity> list) {
        this.list = list;
    }
}
