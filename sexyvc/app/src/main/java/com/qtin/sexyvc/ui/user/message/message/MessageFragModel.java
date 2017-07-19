package com.qtin.sexyvc.ui.user.message.message;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.user.bean.MsgItems;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class MessageFragModel extends BaseModel<ServiceManager,CacheManager> implements MessageFragContract.Model {

    @Inject
    public MessageFragModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<MsgItems>> queryMessage(String token, long id, int page_size) {
        return null;
    }
}
