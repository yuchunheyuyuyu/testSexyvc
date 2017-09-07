package com.qtin.sexyvc.ui.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/9/6.
 */

public class HomeCommentBean {
    private int count;
    private ArrayList<CommentEntity> list;
    private HomeInfoBean info;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<CommentEntity> getList() {
        return list;
    }

    public void setList(ArrayList<CommentEntity> list) {
        this.list = list;
    }

    public HomeInfoBean getInfo() {
        return info;
    }

    public void setInfo(HomeInfoBean info) {
        this.info = info;
    }
}
