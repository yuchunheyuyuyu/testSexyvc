package com.qtin.sexyvc.ui.main.fraghome.bean;

import com.qtin.sexyvc.ui.bean.CommentEntity;
import java.util.ArrayList;
/**
 * Created by ls on 17/6/10.
 */
public class CommentBean {
    private int total;
    private ArrayList<CommentEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<CommentEntity> getList() {
        return list;
    }

    public void setList(ArrayList<CommentEntity> list) {
        this.list = list;
    }
}
