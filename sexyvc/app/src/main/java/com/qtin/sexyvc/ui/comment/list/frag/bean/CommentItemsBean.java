package com.qtin.sexyvc.ui.comment.list.frag.bean;

import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import java.util.ArrayList;

/**
 * Created by ls on 17/7/19.
 */

public class CommentItemsBean {
    private int total;
    private ArrayList<CommentBean> list;

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
