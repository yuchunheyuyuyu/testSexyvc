package com.qtin.sexyvc.ui.concern.list;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.CodeEntity;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class ConcernListModel extends BaseModel<ServiceManager,CacheManager> implements ConcernListContract.Model {

    @Inject
    public ConcernListModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<CodeEntity> queryGroupDetail(String token, long group_id) {
        return mServiceManager.getCommonService().queryGroupDetail(token,group_id);
    }
}
