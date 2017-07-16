package com.qtin.sexyvc.ui.rate;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.request.RateRequest;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface RateContract {
    interface View extends BaseView{
        void rateSuccess(int score);
    }
    interface Model extends IModel{
        String getToken();
        Observable<CodeEntity> rateInvestor(RateRequest request);
    }
}
