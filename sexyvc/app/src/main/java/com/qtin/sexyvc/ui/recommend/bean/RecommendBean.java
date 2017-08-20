package com.qtin.sexyvc.ui.recommend.bean;

import com.qtin.sexyvc.ui.bean.TagEntity;
import java.util.ArrayList;

/**
 * Created by ls on 17/8/17.
 */

public class RecommendBean {
    private long investor_id;
    private String investor_name;
    private String investor_avatar;
    private long investor_uid;

    private float score;
    private int comment_number;
    private long fund_id;
    private String fund_name;

    private String title;
    private ArrayList<TagEntity> tags;
    private boolean isSelected=true;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(long investor_id) {
        this.investor_id = investor_id;
    }

    public String getInvestor_name() {
        return investor_name;
    }

    public void setInvestor_name(String investor_name) {
        this.investor_name = investor_name;
    }

    public String getInvestor_avatar() {
        return investor_avatar;
    }

    public void setInvestor_avatar(String investor_avatar) {
        this.investor_avatar = investor_avatar;
    }

    public long getInvestor_uid() {
        return investor_uid;
    }

    public void setInvestor_uid(long investor_uid) {
        this.investor_uid = investor_uid;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    public long getFund_id() {
        return fund_id;
    }

    public void setFund_id(long fund_id) {
        this.fund_id = fund_id;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<TagEntity> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagEntity> tags) {
        this.tags = tags;
    }
}
/**

 "investor_id": 396,
 "investor_name": "蒋涛",
 "investor_avatar": "image/2016/10/12/e98a5ddadbbde2fe5b562c4924743e8c.jpg",
 "investor_uid": 0,

 "score": "8.7",
 "comment_number": 2,
 "fund_id": 34,
 "fund_name": "戈壁投资",

 "title": "管理合伙人",
 "tags": []
 */