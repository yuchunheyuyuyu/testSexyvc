package com.qtin.sexyvc.ui.user.info;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;

import javax.inject.Inject;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class UserInfoModel extends BaseModel<ServiceManager,CacheManager> implements UserInfoContract.Model {

    @Inject
    public UserInfoModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }
}
