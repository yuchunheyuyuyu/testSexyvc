package com.qtin.sexyvc.ui.bean;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/22.
 */
public class GroupEntity {
    private int total;
    private int contact_count;
    private ArrayList<Long> join_groups;
    private ArrayList<ConcernGroupEntity>list;

    public int getContact_count() {
        return contact_count;
    }

    public void setContact_count(int contact_count) {
        this.contact_count = contact_count;
    }

    public ArrayList<Long> getJoin_groups() {
        return join_groups;
    }

    public void setJoin_groups(ArrayList<Long> join_groups) {
        this.join_groups = join_groups;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<ConcernGroupEntity> getList() {
        return list;
    }

    public void setList(ArrayList<ConcernGroupEntity> list) {
        this.list = list;
    }
}
