package com.qtin.sexyvc.ui.login.password.reset;

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
public class ResetPasswordModel extends BaseModel<ServiceManager,CacheManager> implements ResetPasswordContract.Model {

    @Inject
    public ResetPasswordModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<CodeEntity> resetPassword(String code_value, String mobile, String password) {
        return mServiceManager.getCommonService().resetPassword(code_value,mobile,password);
    }
}
