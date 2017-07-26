package com.qtin.sexyvc.ui.login.password.forget;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class ForgetModel extends BaseModel<ServiceManager,CacheManager> implements ForgetContract.Model {

    @Inject
    public ForgetModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<CodeEntity> getVertifyCode(String token,String mobile, String code_type) {
        return mServiceManager.getCommonService().getVertifyCode(token,mobile,code_type);
    }

    @Override
    public Observable<BaseEntity<BindEntity>> validateCode(String mobile, String code_type, String code_value) {
        return mServiceManager.getCommonService().validateCode(mobile,code_type,code_value);
    }
}
