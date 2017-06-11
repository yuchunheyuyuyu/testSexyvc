package com.qtin.sexyvc.ui.main.fragInvestor.bean;

import com.qtin.sexyvc.ui.bean.InvestorEntity;
import java.util.ArrayList;

/**
 * Created by ls on 17/6/11.
 */

public class InvestorBean {
    private int total;
    private ArrayList<InvestorEntity> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<InvestorEntity> getList() {
        return list;
    }

    public void setList(ArrayList<InvestorEntity> list) {
        this.list = list;
    }
}
