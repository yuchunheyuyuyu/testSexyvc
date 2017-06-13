package com.qtin.sexyvc.ui.login.password.forget;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;

import javax.inject.Inject;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class ForgetModel extends BaseModel<ServiceManager,CacheManager> implements ForgetContract.Model {

    @Inject
    public ForgetModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }
}
