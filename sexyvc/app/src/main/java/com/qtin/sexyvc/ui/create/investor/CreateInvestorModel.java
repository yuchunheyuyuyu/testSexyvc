package com.qtin.sexyvc.ui.create.investor;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.IdBean;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.request.CreateInvestorRequest;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class CreateInvestorModel extends BaseModel<ServiceManager,CacheManager> implements CreateInvestorContract.Model {

    @Inject
    public CreateInvestorModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<IdBean>> createInvestor(CreateInvestorRequest request) {
        return mServiceManager.getCommonService().createInvestor(request);
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
