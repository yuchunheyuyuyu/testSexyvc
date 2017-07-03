package com.qtin.sexyvc.ui.investor.bean;

import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import java.util.ArrayList;

/**
 * Created by ls on 17/7/3.
 */
public class ItemCaseBean implements DataTypeInterface{
    private ArrayList<CaseBean> case_list;

    private int comment_number;
    private int case_number;

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    public int getCase_number() {
        return case_number;
    }

    public void setCase_number(int case_number) {
        this.case_number = case_number;
    }

    public ArrayList<CaseBean> getCase_list() {
        return case_list;
    }

    public void setCase_list(ArrayList<CaseBean> case_list) {
        this.case_list = case_list;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_CASE;
    }
}
