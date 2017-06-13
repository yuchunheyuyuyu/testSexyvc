package com.qtin.sexyvc.ui.login.account.create;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class CreateModel extends BaseModel<ServiceManager,CacheManager> implements CreateContract.Model {

    @Inject
    public CreateModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public void saveUser(UserEntity entity) {
        mCacheManager.getDaoSession().getUserEntityDao().deleteAll();
        mCacheManager.getDaoSession().getUserEntityDao().save(entity);
    }

    @Override
    public Observable<CodeEntity> getVertifyCode(String mobile, String code_type) {
        return mServiceManager.getCommonService().getVertifyCode(mobile,code_type);
    }

    @Override
    public Observable<CodeEntity> validateCode(String mobile, String code_type, String code_value) {
        return mServiceManager.getCommonService().validateCode(mobile,code_type,code_value);
    }

    @Override
    public Observable<BaseEntity<UserEntity>> doRegister(RegisterRequestEntity entity) {
        return mServiceManager.getCommonService().doRegister(entity);
    }
}
