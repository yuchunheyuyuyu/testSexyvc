package com.qtin.sexyvc.ui.user.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/7/19.
 */

public class MsgItems {
    private int total;
    private ArrayList<MsgBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<MsgBean> getList() {
        return list;
    }

    public void setList(ArrayList<MsgBean> list) {
        this.list = list;
    }
}
