package com.qtin.sexyvc.ui.investor;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.request.FollowRequest;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface InvestorDetailContract {
    interface View extends BaseView{
        void querySuccess(CallBackBean backBean);
    }
    interface Model extends IModel{
        String getToken();
        Observable<CodeEntity> followInvestor(FollowRequest entity);
        Observable<BaseEntity<CallBackBean>> queryInvestorDetail(String token,long investor_id,long comment_id);
    }
}
