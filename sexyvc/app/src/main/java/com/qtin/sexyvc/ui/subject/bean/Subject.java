package com.qtin.sexyvc.ui.subject.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/23.
 */

public class Subject {
    private int total;
    private ArrayList<SubjectListEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<SubjectListEntity> getList() {
        return list;
    }

    public void setList(ArrayList<SubjectListEntity> list) {
        this.list = list;
    }
}
