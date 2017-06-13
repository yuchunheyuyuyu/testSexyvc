package com.qtin.sexyvc.ui.login.account.login;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class LoginModel extends BaseModel<ServiceManager,CacheManager> implements LoginContract.Model {

    @Inject
    public LoginModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<UserEntity>> login(String username, int account_type, String password) {
        return mServiceManager.getCommonService().login(username,account_type,password);
    }

    @Override
    public void saveUser(UserEntity entity) {
        List<UserEntity> list=mCacheManager.getDaoSession().getUserEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            mCacheManager.getDaoSession().getUserEntityDao().deleteAll();
        }
        mCacheManager.getDaoSession().getUserEntityDao().save(entity);
    }
}
