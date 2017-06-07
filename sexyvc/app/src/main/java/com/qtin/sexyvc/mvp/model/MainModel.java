package com.qtin.sexyvc.mvp.model;

import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.contract.MainContract;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import javax.inject.Inject;

/**
 * Created by ls on 17/2/27.
 */

public class MainModel extends BaseModel<ServiceManager,CacheManager> implements MainContract.Model {

    @Inject
    public MainModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }
}
