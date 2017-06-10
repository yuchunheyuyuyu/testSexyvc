package com.qtin.sexyvc.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.contract.UploadContract;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.mvp.model.entity.QiniuTokenEntity;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ls on 17/3/1.
 */
@ActivityScope
public class UploadModel extends BaseModel<ServiceManager,CacheManager>implements UploadContract.Model {

    @Inject
    public UploadModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(String url) {
        return mServiceManager.getCommonService().getQiniuToken(url);
    }
}
