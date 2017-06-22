package com.qtin.sexyvc.ui.main.fragconcern;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CreateGroupEntity;
import com.qtin.sexyvc.ui.bean.GroupEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class ConcernModel extends BaseModel<ServiceManager,CacheManager> implements ConcernContract.Model {

    @Inject
    public ConcernModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public String getToken() {
        List<UserEntity> list=mCacheManager.getDaoSession().getUserEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            return list.get(0).getU_token();
        }
        return "";
    }

    @Override
    public Observable<BaseEntity<GroupEntity>> queryInvestorGroup(String token,long investor_id, int page, int page_size) {
        return mServiceManager.getCommonService().queryInvestorGroup(token,investor_id,page,page_size);
    }

    @Override
    public Observable<BaseEntity<CreateGroupEntity>> addInvestorGroup(String token, String group_name) {
        return mServiceManager.getCommonService().addInvestorGroup(token,group_name);
    }

    @Override
    public Observable<CodeEntity> updateInvestorGroup(String token, long group_id, String group_name, int status) {
        return mServiceManager.getCommonService().updateInvestorGroup(token,group_id,group_name,status);
    }
}
