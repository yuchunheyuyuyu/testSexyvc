package com.qtin.sexyvc.ui.fund.detail.bean;

import com.qtin.sexyvc.ui.investor.bean.CommentListBean;

/**
 * Created by ls on 17/7/5.
 */
public class FundDetailBackBean {
    private FundDetailBean fund;
    private CommentListBean comments;
    private CommentListBean roadshows;

    public CommentListBean getRoadshows() {
        return roadshows;
    }

    public void setRoadshows(CommentListBean roadshows) {
        this.roadshows = roadshows;
    }

    public FundDetailBean getFund() {
        return fund;
    }

    public void setFund(FundDetailBean fund) {
        this.fund = fund;
    }

    public CommentListBean getComments() {
        return comments;
    }

    public void setComments(CommentListBean comments) {
        this.comments = comments;
    }
}
