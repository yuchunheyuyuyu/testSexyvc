package com.qtin.sexyvc.ui.investor;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.LastBrowerBean;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.request.FollowRequest;

import java.util.List;

import javax.inject.Inject;

import io.rx_cache.DynamicKey;
import io.rx_cache.Reply;
import rx.Observable;
import rx.functions.Func1;

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
    public Observable<BaseEntity<CallBackBean>> queryInvestorDetail(String token, long investor_id, long comment_id,int page_size,int auth_state) {
        return mServiceManager.getCommonService().queryInvestorDetail(token,investor_id,comment_id,page_size,auth_state);
    }

    @Override
    public void insertLastBrower(LastBrowerBean bean) {
        bean.setLocalTime(System.currentTimeMillis());
        List<LastBrowerBean> list=mCacheManager.getDaoSession().getLastBrowerBeanDao().queryBuilder().build().list();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                if(list.get(i).getInvestor_id()==bean.getInvestor_id()){
                    mCacheManager.getDaoSession().getLastBrowerBeanDao().delete(list.get(i));
                    break;
                }
            }
        }
        mCacheManager.getDaoSession().getLastBrowerBeanDao().insert(bean);
    }

    @Override
    public Observable<Typebean> getType(String type_key) {
        Observable<Typebean> types=mServiceManager.getCommonService().getType(type_key);
        return mCacheManager.getCommonCache().getType(types,new DynamicKey(type_key))
                .flatMap(new Func1<Reply<Typebean>, Observable<Typebean>>() {
                    @Override
                    public Observable<Typebean> call(Reply<Typebean> typebeanReply) {
                        return Observable.just(typebeanReply.getData());
                    }
                });
    }

    @Override
    public Observable<CodeEntity> createProject(ProjectBean request) {
        return mServiceManager.getCommonService().createProject(request);
    }

    @Override
    public void updateProjectState() {
        List<UserInfoEntity> list=mCacheManager.getDaoSession().getUserInfoEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            list.get(0).setHas_project(1);
            mCacheManager.getDaoSession().getUserInfoEntityDao().update(list.get(0));
        }
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
