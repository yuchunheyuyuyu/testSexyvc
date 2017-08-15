package com.qtin.sexyvc.ui.user.sent.bean;

/**
 * Created by ls on 17/8/14.
 */

public class SentBean {
    private float score;
    private long investor_id;
    private String investor_name;
    private long investor_uid;

    private String investor_avatar;
    private long fund_id;
    private String fund_name;
    private int has_comment;

    private long comment_id;
    private int has_roadshow;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
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

    public long getInvestor_uid() {
        return investor_uid;
    }

    public void setInvestor_uid(long investor_uid) {
        this.investor_uid = investor_uid;
    }

    public String getInvestor_avatar() {
        return investor_avatar;
    }

    public void setInvestor_avatar(String investor_avatar) {
        this.investor_avatar = investor_avatar;
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

    public int getHas_comment() {
        return has_comment;
    }

    public void setHas_comment(int has_comment) {
        this.has_comment = has_comment;
    }

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public int getHas_roadshow() {
        return has_roadshow;
    }

    public void setHas_roadshow(int has_roadshow) {
        this.has_roadshow = has_roadshow;
    }
}
/**

 "score": 8,
 "investor_id": 2858,
 "investor_name": "查立",
 "investor_uid": 0,

 "investor_avatar": "image/2017/07/10/cb0f1f7a153150cc7c0975185c16f537.jpg",
 "fund_id": 370,
 "fund_name": "起点创业营",
 "has_comment": 1,

 "comment_id": 367,
 "has_roadshow": 0

 */