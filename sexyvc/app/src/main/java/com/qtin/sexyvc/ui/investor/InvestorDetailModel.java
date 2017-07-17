package com.qtin.sexyvc.ui.investor;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.request.FollowRequest;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class InvestorDetailModel extends BaseModel<ServiceManager,CacheManager> implements InvestorDetailContract.Model {

    @Inject
    public InvestorDetailModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<CodeEntity> followInvestor(FollowRequest entity) {
        return mServiceManager.getCommonService().followInvestor(entity);
    }

    @Override
    public Observable<BaseEntity<CallBackBean>> queryInvestorDetail(String token, long investor_id, long comment_id) {
        return mServiceManager.getCommonService().queryInvestorDetail(token,investor_id,comment_id);
    }


    @Override
    public Observable<CodeEntity> unFollowInvestor(String token, long investor_id) {
        return mServiceManager.getCommonService().unFollowInvestor(token,investor_id);
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
    public UserInfoEntity getUserInfo() {
        List<UserInfoEntity> list=mCacheManager.getDaoSession().getUserInfoEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
