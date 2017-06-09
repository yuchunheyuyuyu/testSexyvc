package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/6/9.
 * 筛选条件
 */
public class FilterEntity {
    private String name;
    private int id;

    public FilterEntity(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public FilterEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
