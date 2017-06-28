package com.qtin.sexyvc.ui.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/27.
 */

public class RepliesBean {
    private int total;
    private ArrayList<ReplyBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<ReplyBean> getList() {
        return list;
    }

    public void setList(ArrayList<ReplyBean> list) {
        this.list = list;
    }
}
