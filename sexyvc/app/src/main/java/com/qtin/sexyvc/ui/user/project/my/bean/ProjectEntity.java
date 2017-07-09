package com.qtin.sexyvc.ui.user.project.my.bean;

import com.qtin.sexyvc.ui.bean.ProjectBean;
import java.util.ArrayList;

/**
 * Created by ls on 17/7/9.
 */

public class ProjectEntity {
    private int total;
    private ArrayList<ProjectBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<ProjectBean> getList() {
        return list;
    }

    public void setList(ArrayList<ProjectBean> list) {
        this.list = list;
    }
}
