package com.qtin.sexyvc.ui.flash;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.flash.bean.FlashBean;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class FlashModel extends BaseModel<ServiceManager,CacheManager> implements FlashContract.Model {

    @Inject
    public FlashModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<FlashBean>> queryFlashList(long flash_id, int page_size) {
        return mServiceManager.getCommonService().queryFlashList(flash_id,page_size);
    }
}
