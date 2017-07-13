package com.qtin.sexyvc.ui.fund.detail;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.fund.detail.bean.FundDetailBackBean;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class FundDetailModel extends BaseModel<ServiceManager,CacheManager> implements FundDetailContract.Model {

    @Inject
    public FundDetailModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<FundDetailBackBean>> queryFundDetail(String token, long fund_id, long comment_id) {
        return mServiceManager.getCommonService().queryFundDetail(token,fund_id,comment_id);
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