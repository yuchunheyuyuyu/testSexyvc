package com.qtin.sexyvc.ui.investor.bean;

import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

/**
 * Created by ls on 17/7/3.
 */
public class ItemBaseBean implements DataTypeInterface {

    private String investor_intro;

    public String getInvestor_intro() {
        return investor_intro;
    }

    public void setInvestor_intro(String investor_intro) {
        this.investor_intro = investor_intro;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_BASE_INFO;
    }
}
