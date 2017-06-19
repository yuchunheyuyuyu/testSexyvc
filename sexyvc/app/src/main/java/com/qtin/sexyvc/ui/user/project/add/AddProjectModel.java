package com.qtin.sexyvc.ui.user.project.add;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class AddProjectModel extends BaseModel<ServiceManager,CacheManager> implements AddProjectContract.Model {

    @Inject
    public AddProjectModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseListEntity<FilterEntity>> getType(String type_key) {
        return mServiceManager.getCommonService().getType(type_key);
    }
}
