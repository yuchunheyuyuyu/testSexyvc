package com.qtin.sexyvc.ui.main.fraghome.entity;

import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;
import java.util.ArrayList;

/**
 * Created by ls on 17/6/10.
 */
public class ItemInvestorEntity implements HomeInterface{

    private ArrayList<InvestorEntity> list;

    public ArrayList<InvestorEntity> getList() {
        return list;
    }

    public void setList(ArrayList<InvestorEntity> list) {
        this.list = list;
    }

    @Override
    public int getType() {
        return HomeAdapter.ITEM_INVESTOR;
    }
}
