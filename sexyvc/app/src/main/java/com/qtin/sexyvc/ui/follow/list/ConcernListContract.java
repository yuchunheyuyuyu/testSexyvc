package com.qtin.sexyvc.ui.follow.list;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface ConcernListContract {
    interface View extends BaseView{
        void startLoadMore();
        void endLoadMore();
        void querySuccess(ConcernEntity entity);
    }
    interface Model extends IModel{
        Observable<BaseEntity<ConcernEntity>> queryGroupDetail(String token, long group_id, int page, int page_size);
        String getToken();
    }
}