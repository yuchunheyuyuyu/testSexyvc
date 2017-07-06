package com.qtin.sexyvc.ui.search.result;

import android.content.Context;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.FundBackEntity;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.request.InvestorRequest;
import java.util.ArrayList;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface SearchResultContract {
    interface View extends BaseView{
        //这里写view中公开出去的方法，供present调用
        Context getContext();
        void requestTypeBack(int type,ArrayList<FilterEntity> list);
        void startLoadMore();
        void endLoadMore();

        void queryFundSuccess(FundBackEntity backEntity);
        void queryInvestorSuccess(InvestorBean bean);
    }
    interface Model extends IModel{

        //这里写Model中公开的方法，在present调用
        String getToken();
        Observable<Typebean> getType(String type_key);

        Observable<BaseEntity<FundBackEntity>> queryFunds(InvestorRequest request);
        Observable<BaseEntity<InvestorBean>> queryInvestors(InvestorRequest request);
    }
}
