package com.qtin.sexyvc.ui.more.object.road;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.investor.bean.CommentListBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface FragRoadCommentContract {
    interface View extends BaseView{
        void showNetErrorView();
        void showContentView();
        void showEmptyView();

        void startLoadMore();
        void endLoadMore();

        void querySuccess(CommentListBean commentListBean);
    }
    interface Model extends IModel{
        Observable<BaseEntity<CommentListBean>> queryInvestorComment(String token,
                                                                     long investor_id,
                                                                     String data_type,
                                                                     String page_type,
                                                                     int page_size,
                                                                     long last_id,
                                                                     int auth_state);

        Observable<BaseEntity<CommentListBean>> queryFundComment(String token,
                                                                 long fund_id,
                                                                 String data_type,
                                                                 String page_type,
                                                                 int page_size,
                                                                 long last_id,
                                                                 int auth_state);

        String getToken();
    }
}
