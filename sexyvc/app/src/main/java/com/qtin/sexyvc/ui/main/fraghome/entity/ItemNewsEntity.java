package com.qtin.sexyvc.ui.main.fraghome.entity;

import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/10.
 */
public class ItemNewsEntity implements HomeInterface {

    private ArrayList<String> list;

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getType() {
        return HomeAdapter.ITEM_NEWS;
    }
}
