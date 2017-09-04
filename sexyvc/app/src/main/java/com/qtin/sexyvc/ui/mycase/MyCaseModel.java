package com.qtin.sexyvc.ui.mycase;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.DeleteCaseRequest;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.bean.UserEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class MyCaseModel extends BaseModel<ServiceManager,CacheManager> implements MyCaseContract.Model {

    @Inject
    public MyCaseModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<ListBean<CaseBean>>> queryMyCase(String token) {
        return mServiceManager.getCommonService().queryMyCase(token);
    }

    @Override
    public Observable<CodeEntity> deleteCase(DeleteCaseRequest request) {
        return mServiceManager.getCommonService().deleteCase(request);
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
