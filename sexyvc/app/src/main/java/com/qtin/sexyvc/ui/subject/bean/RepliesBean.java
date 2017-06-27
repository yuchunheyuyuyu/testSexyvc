package com.qtin.sexyvc.ui.subject.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/27.
 */

public class RepliesBean {
    private int total;
    private ArrayList<SubjectReplyEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<SubjectReplyEntity> getList() {
        return list;
    }

    public void setList(ArrayList<SubjectReplyEntity> list) {
        this.list = list;
    }
}
