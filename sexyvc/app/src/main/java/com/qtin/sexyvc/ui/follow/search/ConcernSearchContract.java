package com.qtin.sexyvc.ui.follow.search;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.ConcernListEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface ConcernSearchContract {
    interface View extends BaseView{
        void searchSuccess(ConcernEntity entity);
    }
    interface Model extends IModel{
        Observable<BaseEntity<ConcernEntity>> searchConcern(String token,String keyword);
        void insertConcern(ConcernListEntity listEntity);
        List<ConcernListEntity> queryLocalHistory();
        void clearHistory();
        String getToken();
    }
}
