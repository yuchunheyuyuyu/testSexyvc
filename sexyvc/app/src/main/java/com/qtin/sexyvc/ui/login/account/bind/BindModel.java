package com.qtin.sexyvc.ui.login.account.bind;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BindEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class BindModel extends BaseModel<ServiceManager,CacheManager> implements BindContract.Model {

    @Inject
    public BindModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<CodeEntity> getVertifyCode(String mobile, String code_type) {
        return mServiceManager.getCommonService().getVertifyCode(mobile,code_type);
    }

    @Override
    public Observable<BaseEntity<BindEntity>> validateCode(String mobile, String code_type, String code_value) {
        return mServiceManager.getCommonService().validateCode(mobile,code_type,code_value);
    }

    @Override
    public Observable<CodeEntity> bindMobile(String token, String mobile, String password) {
        return mServiceManager.getCommonService().bindMobile(token,mobile,password);
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
    public String getToken() {
        List<UserEntity> list=mCacheManager.getDaoSession().getUserEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            return list.get(0).getU_token();
        }
        return "";
    }
}
