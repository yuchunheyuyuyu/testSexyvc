package com.qtin.sexyvc.ui.road.show.bean;

import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.bean.ReplyBean;

/**
 * Created by ls on 17/9/8.
 */
public class RoadShowBean {
    private RoadDetailBean detail;
    private ListBean<ReplyBean> replies;

    public RoadDetailBean getDetail() {
        return detail;
    }

    public void setDetail(RoadDetailBean detail) {
        this.detail = detail;
    }

    public ListBean<ReplyBean> getReplies() {
        return replies;
    }

    public void setReplies(ListBean<ReplyBean> replies) {
        this.replies = replies;
    }
}
