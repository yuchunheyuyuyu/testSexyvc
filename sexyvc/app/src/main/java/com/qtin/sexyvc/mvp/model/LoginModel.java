package com.qtin.sexyvc.mvp.model;

import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.contract.LoginContarct;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.mvp.model.entity.LoginEntity;
import com.qtin.sexyvc.mvp.model.entity.RegisterRequestEntity;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/3/6.
 */
public class LoginModel extends BaseModel<ServiceManager,CacheManager> implements LoginContarct.Model {

    @Inject
    public LoginModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseListEntity<LoginEntity>> register(RegisterRequestEntity entity) {
        return mServiceManager.getCommonService().register(entity);
    }
}
