package com.qtin.sexyvc.ui.login.password.set;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.RegisterRequestEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class SetPasswordModel extends BaseModel<ServiceManager,CacheManager> implements SetPasswordContract.Model {

    @Inject
    public SetPasswordModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<UserEntity>> doRegister(RegisterRequestEntity entity) {
        return mServiceManager.getCommonService().doRegister(entity);
    }

    @Override
    public void saveUser(UserEntity entity) {
        List<UserEntity> list=mCacheManager.getDaoSession().getUserEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            mCacheManager.getDaoSession().getUserEntityDao().deleteAll();
        }
        mCacheManager.getDaoSession().getUserEntityDao().save(entity);
    }
    @Override
    public boolean changeBindStatus(int bind) {
        List<UserEntity> list=mCacheManager.getDaoSession().getUserEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            UserEntity userEntity=list.get(0);
            userEntity.setBind_mobile(1);
            mCacheManager.getDaoSession().getUserEntityDao().update(userEntity);
            return true;
        }
        return false;
    }

    @Override
    public Observable<CodeEntity> bindMobile(String token, String mobile, String password) {
        return mServiceManager.getCommonService().bindMobile(token,mobile,password);
    }

    @Override
    public String getToken() {
        List<UserEntity> list=mCacheManager.getDaoSession().getUserEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            return list.get(0).getU_token();
        }
        return "";
    }
}
