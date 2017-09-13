package com.qtin.sexyvc.ui.follow.list;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.follow.list.bean.FollowedFundBean;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class ConcernListModel extends BaseModel<ServiceManager,CacheManager> implements ConcernListContract.Model {

    @Inject
    public ConcernListModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<ConcernEntity>> queryGroupDetail(String token, long group_id, int page, int page_size) {
        return mServiceManager.getCommonService().queryGroupDetail(token,group_id,page,page_size);
    }

    @Override
    public Observable<BaseEntity<ListBean<FollowedFundBean>>> queryFundDetail(String token, long group_id, int page, int page_size, int object_type) {
        return mServiceManager.getCommonService().queryFundDetail(token,group_id,page,page_size,object_type);
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
