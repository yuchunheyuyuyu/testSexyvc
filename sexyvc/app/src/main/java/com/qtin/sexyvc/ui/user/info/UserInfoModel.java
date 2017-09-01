package com.qtin.sexyvc.ui.user.info;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

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
public class UserInfoModel extends BaseModel<ServiceManager,CacheManager> implements UserInfoContract.Model {

    @Inject
    public UserInfoModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<CodeEntity> editAvatar(String token, String u_avatar) {
        return mServiceManager.getCommonService().editAvatar(token,u_avatar);
    }

    @Override
    public Observable<CodeEntity> editSex(String token, int u_gender) {
        return mServiceManager.getCommonService().editSex(token,u_gender);
    }

    @Override
    public Observable<CodeEntity> uploadVertifyPhoto(String token, String img_url) {
        return mServiceManager.getCommonService().uploadVertifyPhoto(token,img_url);
    }

    @Override
    public Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(int is_protected) {
        return mServiceManager.getCommonService().getQiniuToken(is_protected);
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
    public void saveUsrInfo(UserInfoEntity entity) {
        List<UserInfoEntity> list=mCacheManager.getDaoSession().getUserInfoEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            mCacheManager.getDaoSession().getUserInfoEntityDao().deleteAll();
        }
        mCacheManager.getDaoSession().getUserInfoEntityDao().insert(entity);
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
}
