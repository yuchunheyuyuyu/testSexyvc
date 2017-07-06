package com.qtin.sexyvc.ui.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/7/6.
 */

public class FundBackEntity {
    private int total;
    private ArrayList<FundEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<FundEntity> getList() {
        return list;
    }

    public void setList(ArrayList<FundEntity> list) {
        this.list = list;
    }
}
