package com.qtin.sexyvc.ui.user.influency;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.InfluencyBean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class
InfluencyModel extends BaseModel<ServiceManager,CacheManager> implements InfluencyContract.Model {

    @Inject
    public InfluencyModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<InfluencyBean>> queryInfluency(String token) {
        return mServiceManager.getCommonService().queryInfluency(token);
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
