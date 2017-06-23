package com.qtin.sexyvc.ui.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/23.
 */
public class ConcernEntity {
    private int total;
    private ArrayList<ConcernListEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<ConcernListEntity> getList() {
        return list;
    }

    public void setList(ArrayList<ConcernListEntity> list) {
        this.list = list;
    }
}
