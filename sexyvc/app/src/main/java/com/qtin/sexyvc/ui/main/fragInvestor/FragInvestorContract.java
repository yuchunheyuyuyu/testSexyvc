package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Context;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.request.FollowRequest;

import java.util.ArrayList;
import rx.Observable;
/**
 * Created by ls on 17/4/26.
 */
public interface FragInvestorContract {

    interface View extends BaseView{
        //这里写view中公开出去的方法，供present调用
        Context getContext();
        void requestTypeBack(int type,ArrayList<FilterEntity> list);
        void startLoadMore();
        void endLoadMore();
        void querySuccess(InvestorBean bean);
    }
    interface Model extends IModel{
        //这里写Model中公开的方法，在present调用
        String getToken();
        Observable<BaseListEntity<FilterEntity>> getType(String type_key);
        Observable<BaseEntity<InvestorBean>> querySelectedInvestor(String token,int page,int page_size);
        Observable<CodeEntity> followInvestor(FollowRequest entity);
    }
}
