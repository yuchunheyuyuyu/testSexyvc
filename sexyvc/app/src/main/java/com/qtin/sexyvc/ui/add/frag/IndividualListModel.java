package com.qtin.sexyvc.ui.add.frag;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.LastBrowerBean;
import com.qtin.sexyvc.ui.bean.LastBrowerBeanDao;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;

import java.util.List;

import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class IndividualListModel extends BaseModel<ServiceManager,CacheManager> implements IndividualListContract.Model {

    @Inject
    public IndividualListModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<ConcernEntity>> queryGroupDetail(String token, long group_id, int page, int page_size) {
        return mServiceManager.getCommonService().queryGroupDetail(token,group_id,page,page_size);
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
    public List<LastBrowerBean> queryLastBrowers() {
        List<LastBrowerBean> list=mCacheManager.getDaoSession().getLastBrowerBeanDao().queryBuilder().orderDesc(LastBrowerBeanDao.Properties.LocalTime).build().list();
        return list;
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
