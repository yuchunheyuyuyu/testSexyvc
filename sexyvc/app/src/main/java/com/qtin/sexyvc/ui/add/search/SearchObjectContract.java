package com.qtin.sexyvc.ui.add.search;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.request.InvestorRequest;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface SearchObjectContract {
    interface View extends BaseView{
        void startLoadMore();
        void endLoadMore();
        void queryInvestorSuccess(InvestorBean bean);
        void queryDetailSuccess(CallBackBean backBean);
        void startRefresh(String msg);
        void endRefresh();
    }
    interface Model extends IModel{
        Observable<BaseEntity<InvestorBean>> queryInvestors(InvestorRequest request);
        //这里写Model中公开的方法，在present调用
        String getToken();
        Observable<BaseEntity<CallBackBean>> queryInvestorDetail(String token, long investor_id, long comment_id);
        UserInfoEntity getUserInfo();
    }
}
