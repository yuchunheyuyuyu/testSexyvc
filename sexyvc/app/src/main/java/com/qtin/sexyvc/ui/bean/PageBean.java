package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/7/27.
 */

public class PageBean {
    private long page_id;
    private String page_name;
    private String page_content;
    private String alias_name;

    public long getPage_id() {
        return page_id;
    }

    public void setPage_id(long page_id) {
        this.page_id = page_id;
    }

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public String getPage_content() {
        return page_content;
    }

    public void setPage_content(String page_content) {
        this.page_content = page_content;
    }

    public String getAlias_name() {
        return alias_name;
    }

    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }
}
/**
 *
 *  "page_id": 1,
 "page_name": "他的问题怎么样？",
 "page_content": "他的问题怎么样？",
 "alias_name": "test"
 */