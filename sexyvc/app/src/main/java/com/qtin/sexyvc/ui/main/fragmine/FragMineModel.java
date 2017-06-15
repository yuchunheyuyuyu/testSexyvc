package com.qtin.sexyvc.ui.main.fragmine;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/25.
 */
@FragmentScope
public class FragMineModel extends BaseModel<ServiceManager,CacheManager> implements FragMineContract.Model{

    @Inject
    public FragMineModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<UserInfoEntity>> getUserInfo(String token) {
        return mServiceManager.getCommonService().getUserInfo(token);
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
