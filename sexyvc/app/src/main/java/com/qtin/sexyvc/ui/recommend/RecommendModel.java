package com.qtin.sexyvc.ui.recommend;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.ListBean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.recommend.bean.RecommendBean;
import com.qtin.sexyvc.ui.request.FollowRequest;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class RecommendModel extends BaseModel<ServiceManager,CacheManager> implements RecommendContract.Model {

    @Inject
    public RecommendModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<ListBean<RecommendBean>>> queryRecommend() {
        return mServiceManager.getCommonService().queryRecommend();
    }

    @Override
    public Observable<CodeEntity> followInvestor(FollowRequest entity) {
        return mServiceManager.getCommonService().followInvestor(entity);
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
