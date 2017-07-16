package com.qtin.sexyvc.ui.review;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class ReviewModel extends BaseModel<ServiceManager,CacheManager> implements ReviewContract.Model {

    @Inject
    public ReviewModel(ServiceManager serviceManager, CacheManager cacheManager) {
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
    public Observable<CodeEntity> commentInvestor(String token, String title, String content, long investor_id, long fund_id,int is_anon) {
        return mServiceManager.getCommonService().commentInvestor(token,title,content,investor_id,fund_id,is_anon);
    }

    @Override
    public Observable<CodeEntity> appendInvestor(String token, String content, long comment_id) {
        return mServiceManager.getCommonService().appendInvestor(token,content,comment_id);
    }
}
