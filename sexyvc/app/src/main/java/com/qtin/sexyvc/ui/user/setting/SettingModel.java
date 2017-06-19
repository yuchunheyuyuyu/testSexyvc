package com.qtin.sexyvc.ui.user.setting;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.UserEntity;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class SettingModel extends BaseModel<ServiceManager,CacheManager> implements SettingContract.Model {

    @Inject
    public SettingModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public void logout() {
        List<UserEntity> list=mCacheManager.getDaoSession().getUserEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            mCacheManager.getDaoSession().getUserEntityDao().deleteAll();
        }
    }
}
