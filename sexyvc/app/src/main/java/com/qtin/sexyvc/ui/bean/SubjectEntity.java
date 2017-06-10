package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;

/**
 * Created by ls on 17/6/10.
 */
public class SubjectEntity implements HomeInterface{
    private String subject_id;
    private String subject_cover;
    private String subject_title;
    private String fromString;

    private boolean isFirst;//自定义字段，是否是第一条专题
    private boolean isLast;//自定义字段，是否是最后一条

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_cover() {
        return subject_cover;
    }

    public void setSubject_cover(String subject_cover) {
        this.subject_cover = subject_cover;
    }

    public String getSubject_title() {
        return subject_title;
    }

    public void setSubject_title(String subject_title) {
        this.subject_title = subject_title;
    }

    public String getFromString() {
        return fromString;
    }

    public void setFromString(String fromString) {
        this.fromString = fromString;
    }

    @Override
    public int getType() {
        return HomeAdapter.ITEM_SUBJECT;
    }
}
