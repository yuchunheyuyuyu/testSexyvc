package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;

/**
 * Created by ls on 17/6/10.
 */
public class SubjectEntity implements HomeInterface{

    private String title;
    private String img_url;
    private String source;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


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



    @Override
    public int getType() {
        return HomeAdapter.ITEM_SUBJECT;
    }
}
