package com.qtin.sexyvc.ui.main.fraghome;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.main.fraghome.bean.HomeBean;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class FragHomeModel extends BaseModel<ServiceManager,CacheManager> implements FragHomeContract.Model {

    @Inject
    public FragHomeModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<HomeBean>> queryHome() {
        return mServiceManager.getCommonService().queryHome();
    }
}
