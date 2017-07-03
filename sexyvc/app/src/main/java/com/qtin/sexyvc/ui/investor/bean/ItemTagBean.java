package com.qtin.sexyvc.ui.investor.bean;

import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import java.util.ArrayList;

/**
 * Created by ls on 17/7/3.
 */

public class ItemTagBean implements DataTypeInterface{

    private ArrayList<FilterEntity> domain_list;
    private ArrayList<FilterEntity> stage_list;

    public ArrayList<FilterEntity> getDomain_list() {
        return domain_list;
    }

    public void setDomain_list(ArrayList<FilterEntity> domain_list) {
        this.domain_list = domain_list;
    }

    public ArrayList<FilterEntity> getStage_list() {
        return stage_list;
    }

    public void setStage_list(ArrayList<FilterEntity> stage_list) {
        this.stage_list = stage_list;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_TAG;
    }
}
