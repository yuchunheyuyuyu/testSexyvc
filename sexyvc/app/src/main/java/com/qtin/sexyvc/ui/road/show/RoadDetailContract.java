package com.qtin.sexyvc.ui.road.show;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.road.show.bean.RoadShowBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface RoadDetailContract {
    interface View extends BaseView{
        void startLoadMore();
        void endLoadMore();
    }
    interface Model extends IModel{
        Observable<BaseEntity<RoadShowBean>> queryRoadDetail(String token,long id,int page_size,long last_id);
        String getToken();
    }
}