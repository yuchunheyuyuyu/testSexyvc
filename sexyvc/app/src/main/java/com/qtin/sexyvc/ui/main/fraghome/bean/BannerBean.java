package com.qtin.sexyvc.ui.main.fraghome.bean;

import com.qtin.sexyvc.ui.bean.BannerEntity;
import java.util.ArrayList;

/**
 * Created by ls on 17/6/10.
 */

public class BannerBean {
    private int total;
    private ArrayList<BannerEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<BannerEntity> getList() {
        return list;
    }

    public void setList(ArrayList<BannerEntity> list) {
        this.list = list;
    }
}
