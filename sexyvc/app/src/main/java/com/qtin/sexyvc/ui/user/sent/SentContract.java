package com.qtin.sexyvc.ui.user.sent;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.user.sent.bean.SentBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface SentContract {
    interface View extends BaseView{
        void querySuccess(long record_id,ListBean<SentBean> listBean);
        void startLoadMore();
        void endLoadMore();
    }
    interface Model extends IModel{
        String getToken();
        Observable<BaseEntity<ListBean<SentBean>>> queryMySent(String token,int page_size,long record_id);
        UserInfoEntity getUserInfo();
    }
}
