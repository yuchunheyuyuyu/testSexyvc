package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

import java.util.ArrayList;

/**
 * Created by ls on 17/9/14.
 */

public class HotSearchBean implements DataTypeInterface{
    private ArrayList<String> list;

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_HOT_SEARCH;
    }
}
