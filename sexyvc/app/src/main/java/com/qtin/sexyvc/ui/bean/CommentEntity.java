package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;

/**
 * Created by ls on 17/6/10.
 */
public class CommentEntity implements HomeInterface{
    private String u_title;
    private String domain_name;
    private long domain_id;
    private String u_nickname;

    private long comment_id;
    private String title;
    private int is_anon;
    private long fund_id;

    private String fund_name;
    private long investor_id;
    private String investor_name;
    private float score;

    public String getU_title() {
        return u_title;
    }

    public void setU_title(String u_title) {
        this.u_title = u_title;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public long getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(long domain_id) {
        this.domain_id = domain_id;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIs_anon() {
        return is_anon;
    }

    public void setIs_anon(int is_anon) {
        this.is_anon = is_anon;
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
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
        return HomeAdapter.ITEM_COMMENT;
    }
}
