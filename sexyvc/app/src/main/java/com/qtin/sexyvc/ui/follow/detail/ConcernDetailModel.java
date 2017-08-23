package com.qtin.sexyvc.ui.follow.detail;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.ContactBean;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.request.UnFollowContactRequest;

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
public class ConcernDetailModel extends BaseModel<ServiceManager,CacheManager> implements ConcernDetailContract.Model {

    @Inject
    public ConcernDetailModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<ContactBean>> queryContactDetail(String token, long contact_id) {
        return mServiceManager.getCommonService().queryContactDetail(token,contact_id);
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
    public Observable<CodeEntity> unFollowContact(UnFollowContactRequest request) {
        return mServiceManager.getCommonService().unFollowContact(request);
    }

    @Override
    public UserInfoEntity getUserInfo() {
        List<UserInfoEntity> list=mCacheManager.getDaoSession().getUserInfoEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
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
}
