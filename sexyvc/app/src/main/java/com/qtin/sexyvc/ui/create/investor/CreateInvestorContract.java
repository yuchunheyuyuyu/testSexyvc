package com.qtin.sexyvc.ui.create.investor;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.IdBean;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import com.qtin.sexyvc.ui.request.CreateInvestorRequest;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface CreateInvestorContract {
    interface View extends BaseView{
        void onCreateSuccess(CreateInvestorRequest request);
    }
    interface Model extends IModel{
        Observable<BaseEntity<IdBean>> createInvestor(CreateInvestorRequest request);
        Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(int is_protected);
        String getToken();
    }
}
