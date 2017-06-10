package com.qtin.sexyvc.ui.main.fraghome.entity;

import com.qtin.sexyvc.ui.bean.BannerEntity;
import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;
import java.util.ArrayList;
/**
 * Created by ls on 17/6/10.
 */
public class ItemBannerEntity implements HomeInterface {

    private ArrayList<BannerEntity> list;

    public ArrayList<BannerEntity> getList() {
        return list;
    }

    public void setList(ArrayList<BannerEntity> list) {
        this.list = list;
    }

    @Override
    public int getType() {
        return HomeAdapter.ITEM_BANNER;
    }
}
