package com.qtin.sexyvc.ui.comment.chosen.frag;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.bean.TopicBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface CommentChosenContract {
    interface View extends BaseView{
        void querySuccess(ListBean<TopicBean> bean);
        void startLoadMore();
        void endLoadMore();

        void showNetErrorView();
        void showContentView();
        void showEmptyView();
    }
    interface Model extends IModel{
        String getToken();
        Observable<BaseEntity<ListBean<TopicBean>>> queryTopics(String keyword,long last_id,int page_size);
    }
}
