package com.qtin.sexyvc.ui.main.fragconcern;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;

import javax.inject.Inject;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class ConcernModel extends BaseModel<ServiceManager,CacheManager> implements ConcernContract.Model {

    @Inject
    public ConcernModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }
}
