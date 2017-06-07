package com.qtin.sexyvc.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.contract.RefreshContarct;
import com.qtin.sexyvc.mvp.model.api.Api;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.mvp.model.entity.User;

import java.util.List;
import javax.inject.Inject;
import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ls on 17/2/28.
 */
@ActivityScope
public class RefreshModel extends BaseModel<ServiceManager, CacheManager> implements RefreshContarct.Model {

    public static final int USERS_PER_PAGE = 10;

    @Inject
    public RefreshModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<List<User>> getUser(int lastIdQueried, boolean update) {
        Observable<List<User>> users = mServiceManager.getUserService().getUsers(Api.APP_DOMAIN,lastIdQueried, USERS_PER_PAGE);
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return mCacheManager.getCommonCache()
                .getUsers(users
                        , new DynamicKey(lastIdQueried)
                        , new EvictDynamicKey(update))
                .flatMap(new Func1<Reply<List<User>>, Observable<List<User>>>() {
                    @Override
                    public Observable<List<User>> call(Reply<List<User>> listReply) {
                        return Observable.just(listReply.getData());
                    }
                });
    }
}
