package com.qtin.sexyvc.ui.main.fraghome.bean;

import com.qtin.sexyvc.ui.bean.SubjectEntity;
import java.util.ArrayList;

/**
 * Created by ls on 17/6/10.
 */
public class SubjectBean {
    private int total;
    private ArrayList<SubjectEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<SubjectEntity> getList() {
        return list;
    }

    public void setList(ArrayList<SubjectEntity> list) {
        this.list = list;
    }
}
