package com.qtin.sexyvc.ui.user.message.notice;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.request.ChangeReadStatusRequest;
import com.qtin.sexyvc.ui.user.bean.MsgItems;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class NoticeFragModel extends BaseModel<ServiceManager,CacheManager> implements NoticeFragContract.Model {

    @Inject
    public NoticeFragModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<MsgItems>> queryNotice(String token, long id, int page_size) {
        return mServiceManager.getCommonService().queryNotice(token,id,page_size);
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
    public Observable<CodeEntity> changeReadStatus(ChangeReadStatusRequest request) {
        return mServiceManager.getCommonService().changeReadStatus(request);
    }
}
