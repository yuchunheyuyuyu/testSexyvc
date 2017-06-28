package com.qtin.sexyvc.ui.comment.detail.bean;

import com.qtin.sexyvc.ui.bean.AdditionalBean;
import com.qtin.sexyvc.ui.bean.RepliesBean;
import java.util.ArrayList;

/**
 * Created by ls on 17/6/28.
 */

public class CommentBean {
    private CommentContentBean detail;
    private ArrayList<AdditionalBean> additional;
    private RepliesBean replies;

    public CommentContentBean getDetail() {
        return detail;
    }

    public void setDetail(CommentContentBean detail) {
        this.detail = detail;
    }

    public ArrayList<AdditionalBean> getAdditional() {
        return additional;
    }

    public void setAdditional(ArrayList<AdditionalBean> additional) {
        this.additional = additional;
    }

    public RepliesBean getReplies() {
        return replies;
    }

    public void setReplies(RepliesBean replies) {
        this.replies = replies;
    }
}
