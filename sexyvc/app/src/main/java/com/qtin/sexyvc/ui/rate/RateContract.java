package com.qtin.sexyvc.ui.rate;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CommonBean;
import com.qtin.sexyvc.ui.request.RateRequest;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface RateContract {
    interface View extends BaseView{
        void rateSuccess(int score);
        void queryNormalQuestionsSuccess(CommonBean commonBean);
    }
    interface Model extends IModel{
        String getToken();
        Observable<CodeEntity> rateInvestor(RateRequest request);
        Observable<BaseEntity<CommonBean>> queryNormalQuestion();
    }
}
