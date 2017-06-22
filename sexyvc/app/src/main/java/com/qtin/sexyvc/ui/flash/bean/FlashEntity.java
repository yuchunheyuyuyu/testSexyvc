package com.qtin.sexyvc.ui.flash.bean;

/**
 * Created by ls on 17/6/22.
 */

public class FlashEntity {
    private long id;
    private String content;
    private long create_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
}
