package com.qtin.sexyvc.ui.comment.chosen.detail;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.HomeCommentBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface ChosenDetailContract {
    interface View extends BaseView{
        void showNetErrorView();
        void showContentView();
        void showEmptyView();

        void startLoadMore();
        void endLoadMore();

        void querySuccess(int page,HomeCommentBean homeCommentBean);
    }
    interface Model extends IModel{
        Observable<BaseEntity<HomeCommentBean>> queryTopicDetail(String token,long topic_id, int page, int page_size);
        String getToken();
    }
}
