package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;

/**
 * Created by ls on 17/6/10.
 */
public class CommentEntity implements HomeInterface{
    private String u_avatar;
    private String u_upload_avatar;
    private float comment_score;
    private String comment_title;
    private String tag;

    private int is_anon;
    private String u_nickname;
    private String investor_name;
    private String investor_id;
    private String fund_name;
    private String fund_id;

    private boolean isFirst;//自定义字段，是否是第一条评论
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

    public String getU_avatar() {
        return u_avatar;
    }

    public void setU_avatar(String u_avatar) {
        this.u_avatar = u_avatar;
    }

    public String getU_upload_avatar() {
        return u_upload_avatar;
    }

    public void setU_upload_avatar(String u_upload_avatar) {
        this.u_upload_avatar = u_upload_avatar;
    }

    public float getComment_score() {
        return comment_score;
    }

    public void setComment_score(float comment_score) {
        this.comment_score = comment_score;
    }

    public String getComment_title() {
        return comment_title;
    }

    public void setComment_title(String comment_title) {
        this.comment_title = comment_title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getIs_anon() {
        return is_anon;
    }

    public void setIs_anon(int is_anon) {
        this.is_anon = is_anon;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public String getInvestor_name() {
        return investor_name;
    }

    public void setInvestor_name(String investor_name) {
        this.investor_name = investor_name;
    }

    public String getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(String investor_id) {
        this.investor_id = investor_id;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getFund_id() {
        return fund_id;
    }

    public void setFund_id(String fund_id) {
        this.fund_id = fund_id;
    }

    @Override
    public int getType() {
        return HomeAdapter.ITEM_COMMENT;
    }
}
