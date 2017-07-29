package com.qtin.sexyvc.ui.web;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.demo.activity.bean.PageBean;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class WebModel extends BaseModel<ServiceManager,CacheManager> implements WebContract.Model {

    @Inject
    public WebModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<PageBean>> queryPage(String alias_name) {
        return mServiceManager.getCommonService().queryPage(alias_name);
    }
}
