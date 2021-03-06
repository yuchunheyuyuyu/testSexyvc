package com.qtin.sexyvc.ui.user.message.notice;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.request.ChangeReadStatusRequest;
import com.qtin.sexyvc.ui.user.bean.MsgItems;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface NoticeFragContract {
    interface View extends BaseView{
        void querySuccess(MsgItems items);
        void startLoadMore();
        void endLoadMore();
        void changeStatusSuccess(int position);

        void showNetErrorView();
        void showContentView();
        void showEmptyView();
    }
    interface Model extends IModel{
        Observable<BaseEntity<MsgItems>> queryNotice(String token,long id,int page_size);
        String getToken();
        Observable<CodeEntity> changeReadStatus(ChangeReadStatusRequest request);
    }
}
