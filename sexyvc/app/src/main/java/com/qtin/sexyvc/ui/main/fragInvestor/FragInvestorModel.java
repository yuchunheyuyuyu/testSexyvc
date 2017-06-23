package com.qtin.sexyvc.ui.main.fragInvestor;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.request.FollowRequest;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class FragInvestorModel extends BaseModel<ServiceManager,CacheManager> implements FragInvestorContract.Model {

    @Inject
    public FragInvestorModel(ServiceManager serviceManager, CacheManager cacheManager) {
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
    public Observable<BaseListEntity<FilterEntity>> getType(String type_key) {
        return mServiceManager.getCommonService().getType(type_key);
    }

    @Override
    public Observable<BaseEntity<InvestorBean>> querySelectedInvestor(String token, int page, int page_size) {
        return mServiceManager.getCommonService().querySelectedInvestor(token,page,page_size);
    }

    @Override
    public Observable<CodeEntity> followInvestor(FollowRequest entity) {
        return mServiceManager.getCommonService().followInvestor(entity);
    }
}
