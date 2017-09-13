package com.qtin.sexyvc.ui.improve.fund;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.CodeEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface ImproveFundContract {
    interface View extends BaseView{
        void addSuccess();
    }
    interface Model extends IModel{
        Observable<CodeEntity> addFund(String token,String supply_name,String supply_content);
        String getToken();
    }
}
