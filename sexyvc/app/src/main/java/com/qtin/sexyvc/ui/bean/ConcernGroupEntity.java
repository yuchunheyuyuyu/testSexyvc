package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/6/20.
 */

public class ConcernGroupEntity {

    private String name;
    private int number;

    public ConcernGroupEntity(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
