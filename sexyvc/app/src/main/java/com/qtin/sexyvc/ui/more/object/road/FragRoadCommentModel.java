package com.qtin.sexyvc.ui.more.object.road;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.investor.bean.CommentListBean;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class FragRoadCommentModel extends BaseModel<ServiceManager,CacheManager> implements FragRoadCommentContract.Model {

    @Inject
    public FragRoadCommentModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<CommentListBean>> queryInvestorComment(String token, long investor_id, String data_type, String page_type, int page_size, long last_id) {
        return mServiceManager.getCommonService().queryInvestorComment(token,investor_id,data_type,page_type,page_size,last_id);
    }

    @Override
    public Observable<BaseEntity<CommentListBean>> queryFundComment(String token, long fund_id, String data_type, String page_type, int page_size, long last_id) {
        return mServiceManager.getCommonService().queryFundComment(token,fund_id,data_type,page_type,page_size,last_id);
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
