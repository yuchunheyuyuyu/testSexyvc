package com.qtin.sexyvc.ui.user.message.notice;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;

import javax.inject.Inject;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class NoticeFragModel extends BaseModel<ServiceManager,CacheManager> implements NoticeFragContract.Model {

    @Inject
    public NoticeFragModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }
}
