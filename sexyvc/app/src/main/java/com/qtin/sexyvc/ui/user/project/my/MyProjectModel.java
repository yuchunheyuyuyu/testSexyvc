package com.qtin.sexyvc.ui.user.project.my;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.user.project.my.bean.ProjectEntity;

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
public class MyProjectModel extends BaseModel<ServiceManager,CacheManager> implements MyProjectContract.Model {

    @Inject
    public MyProjectModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<ProjectEntity>> queryMyProject(String token) {
        return mServiceManager.getCommonService().queryMyProject(token);
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
