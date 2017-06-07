package com.qtin.sexyvc.ui.main.fragmine;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import javax.inject.Inject;

/**
 * Created by ls on 17/4/25.
 */
@FragmentScope
public class FragMineModel extends BaseModel<ServiceManager,CacheManager> implements FragMineContract.Model{

    @Inject
    public FragMineModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }
}
