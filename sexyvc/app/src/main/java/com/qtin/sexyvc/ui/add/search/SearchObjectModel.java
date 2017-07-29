package com.qtin.sexyvc.ui.add.search;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.main.fragInvestor.bean.InvestorBean;
import com.qtin.sexyvc.ui.request.InvestorRequest;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class SearchObjectModel extends BaseModel<ServiceManager,CacheManager> implements SearchObjectContract.Model {

    @Inject
    public SearchObjectModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<InvestorBean>> queryInvestors(InvestorRequest request) {
        return mServiceManager.getCommonService().queryInvestors(request);
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
    public Observable<BaseEntity<CallBackBean>> queryInvestorDetail(String token, long investor_id, long comment_id,int page_size) {
        return mServiceManager.getCommonService().queryInvestorDetail(token,investor_id,comment_id,page_size);
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
