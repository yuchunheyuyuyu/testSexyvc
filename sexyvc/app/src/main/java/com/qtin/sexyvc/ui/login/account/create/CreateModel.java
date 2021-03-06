package com.qtin.sexyvc.ui.login.account.create;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.RegisterStatusBean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

import java.util.List;

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
        List<UserEntity> list=mCacheManager.getDaoSession().getUserEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            mCacheManager.getDaoSession().getUserEntityDao().deleteAll();
        }
        mCacheManager.getDaoSession().getUserEntityDao().insert(entity);
    }
    @Override
    public Observable<BaseEntity<UserEntity>> doRegister(RegisterRequestEntity entity) {
        return mServiceManager.getCommonService().doRegister(entity);
    }

    @Override
    public boolean isLogin() {
        List<UserEntity> list=mCacheManager.getDaoSession().getUserEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            if(list.get(0).getBind_mobile()==1){
                return true;
            }
        }
        return false;
    }



    @Override
    public void saveUsrInfo(UserInfoEntity entity) {
        List<UserInfoEntity> list=mCacheManager.getDaoSession().getUserInfoEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            mCacheManager.getDaoSession().getUserInfoEntityDao().deleteAll();
        }
        mCacheManager.getDaoSession().getUserInfoEntityDao().insert(entity);
    }

    @Override
    public Observable<BaseEntity<RegisterStatusBean>> checkRegisterStatus(String mobile) {
        return mServiceManager.getCommonService().checkRegisterStatus(mobile);
    }
}
