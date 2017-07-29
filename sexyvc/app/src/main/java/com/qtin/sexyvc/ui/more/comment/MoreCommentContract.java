package com.qtin.sexyvc.ui.more.comment;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.fund.detail.bean.FundDetailBackBean;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface MoreCommentContract {
    interface View extends BaseView{
        void querySuccess(FundDetailBackBean bean);
        void querySuccess(CallBackBean backBean);
        void startLoadMore();
        void endLoadMore();
    }
    interface Model extends IModel{
        Observable<BaseEntity<CallBackBean>> queryInvestorDetail(String token,long investor_id,long comment_id,int page_size);
        Observable<BaseEntity<FundDetailBackBean>> queryFundDetail(String token, long fund_id, long comment_id,int page_size);
        String getToken();
    }
}
