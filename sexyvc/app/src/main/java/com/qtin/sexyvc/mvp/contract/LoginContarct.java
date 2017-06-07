package com.qtin.sexyvc.mvp.contract;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.mvp.model.entity.BaseListEntity;
import com.qtin.sexyvc.mvp.model.entity.LoginEntity;
import com.qtin.sexyvc.mvp.model.entity.RegisterRequestEntity;

import rx.Observable;

/**
 * Created by ls on 17/3/6.
 */

public interface LoginContarct {

    interface View extends BaseView{

    }
    interface Model extends IModel{
        Observable<BaseListEntity<LoginEntity>> register(RegisterRequestEntity entity);
    }
}
