package com.qtin.sexyvc.ui.recommend;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.recommend.bean.RecommendBean;
import com.qtin.sexyvc.ui.request.FollowRequest;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface RecommendContract {
    interface View extends BaseView{
        void querySuccess(ListBean<RecommendBean> listBean);
        void followSuccess();
        void startRefresh(String msg);
        void endRefresh();
    }
    interface Model extends IModel{
        Observable<BaseEntity<ListBean<RecommendBean>>> queryRecommend();
        Observable<CodeEntity> followInvestor(FollowRequest entity);
        String getToken();
    }
}
