package com.qtin.sexyvc.ui.user.info;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class UserInfoModel extends BaseModel<ServiceManager,CacheManager> implements UserInfoContract.Model {

    @Inject
    public UserInfoModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<CodeEntity> editAvatar(String token, String u_avatar) {
        return mServiceManager.getCommonService().editAvatar(token,u_avatar);
    }

    @Override
    public Observable<CodeEntity> editSex(String token, int u_gender) {
        return mServiceManager.getCommonService().editSex(token,u_gender);
    }

    @Override
    public Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(int is_protected) {
        return mServiceManager.getCommonService().getQiniuToken(is_protected);
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
