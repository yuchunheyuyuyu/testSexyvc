package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/6/22.
 */

public class TagEntity {
    private int tag_count;
    private long tag_id;
    private String tag_name;

    public TagEntity(long tag_id, String tag_name) {
        this.tag_id = tag_id;
        this.tag_name = tag_name;
    }

    public TagEntity() {

    }


    public int getTag_count() {
        return tag_count;
    }

    public void setTag_count(int tag_count) {
        this.tag_count = tag_count;
    }

    public long getTag_id() {
        return tag_id;
    }

    public void setTag_id(long tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
