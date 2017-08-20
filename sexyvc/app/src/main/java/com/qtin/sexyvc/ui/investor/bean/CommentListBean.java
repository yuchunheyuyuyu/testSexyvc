package com.qtin.sexyvc.ui.investor.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/7/3.
 */
public class CommentListBean {

    private int total;
    private ArrayList<CommentBean> list;
    private int unauth_count;

    public int getUnauth_count() {
        return unauth_count;
    }

    public void setUnauth_count(int unauth_count) {
        this.unauth_count = unauth_count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<CommentBean> getList() {
        return list;
    }

    public void setList(ArrayList<CommentBean> list) {
        this.list = list;
    }
}
