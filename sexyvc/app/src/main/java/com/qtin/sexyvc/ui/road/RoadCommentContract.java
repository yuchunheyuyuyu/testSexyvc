package com.qtin.sexyvc.ui.road;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.road.bean.QuestionBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface RoadCommentContract {
    interface View extends BaseView{
        void queryFail();
        void querySuccess(BaseListEntity<QuestionBean> listEntity);
        void startLoad(String msg);
        void endLoad();
    }
    interface Model extends IModel{
        Observable<BaseListEntity<QuestionBean>> queryRoadQuestion(String token);
        String getToken();
    }
}
