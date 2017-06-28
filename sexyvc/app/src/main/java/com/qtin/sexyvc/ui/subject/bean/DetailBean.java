package com.qtin.sexyvc.ui.subject.bean;

import com.qtin.sexyvc.ui.bean.RepliesBean;

/**
 * Created by ls on 17/6/27.
 */

public class DetailBean {
    private SubjectContentEntity detail;
    private RepliesBean replies;

    public SubjectContentEntity getDetail() {
        return detail;
    }

    public void setDetail(SubjectContentEntity detail) {
        this.detail = detail;
    }

    public RepliesBean getReplies() {
        return replies;
    }

    public void setReplies(RepliesBean replies) {
        this.replies = replies;
    }
}
