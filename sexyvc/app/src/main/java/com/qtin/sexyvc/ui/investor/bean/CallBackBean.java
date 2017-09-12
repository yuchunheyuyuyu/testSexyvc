package com.qtin.sexyvc.ui.investor.bean;

/**
 * Created by ls on 17/7/3.
 */

public class CallBackBean {
    private InvestorBean investor;
    private CommentListBean comments;
    private CommentListBean roadshows;

    public CommentListBean getRoadshows() {
        return roadshows;
    }

    public void setRoadshows(CommentListBean roadshows) {
        this.roadshows = roadshows;
    }

    public InvestorBean getInvestor() {
        return investor;
    }

    public void setInvestor(InvestorBean investor) {
        this.investor = investor;
    }

    public CommentListBean getComments() {
        return comments;
    }

    public void setComments(CommentListBean comments) {
        this.comments = comments;
    }
}
