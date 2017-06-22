package com.qtin.sexyvc.ui.flash.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/22.
 */
public class FlashBean {
    private int total;
    private ArrayList<FlashEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<FlashEntity> getList() {
        return list;
    }

    public void setList(ArrayList<FlashEntity> list) {
        this.list = list;
    }
}
