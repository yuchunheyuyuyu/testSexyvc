package com.qtin.sexyvc.ui.fund.detail;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.FundFollowRequest;
import com.qtin.sexyvc.ui.bean.FundUnFollowRequest;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.fund.detail.bean.FundDetailBackBean;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface FundDetailContract {
    interface View extends BaseView{
        void querySuccess(FundDetailBackBean bean);
        void startRefresh(String msg);
        void endRefresh();

        void followSuccess();
        void cancleSuccess();
    }
    interface Model extends IModel{
        Observable<BaseEntity<FundDetailBackBean>> queryFundDetail(String token,long fund_id,long comment_id,int page_size,int auth_state);
        String getToken();
        UserInfoEntity getUserInfo();

        Observable<CodeEntity> followFund(FundFollowRequest entity);
        Observable<CodeEntity> unFollowFund(FundUnFollowRequest entity);
    }
}
