package com.qtin.sexyvc.ui.main.fraghome.entity;

import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;
import com.qtin.sexyvc.ui.main.fraghome.bean.NewsBean;
import java.util.ArrayList;

/**
 * Created by ls on 17/6/10.
 */
public class ItemNewsEntity implements HomeInterface {

    private ArrayList<NewsBean> list;


    public ArrayList<NewsBean> getList() {
        return list;
    }

    public void setList(ArrayList<NewsBean> list) {
        this.list = list;
    }

    @Override
    public int getType() {
        return HomeAdapter.ITEM_NEWS;
    }
}
