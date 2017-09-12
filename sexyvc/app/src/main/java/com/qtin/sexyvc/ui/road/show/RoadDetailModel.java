package com.qtin.sexyvc.ui.road.show;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.ReplyIdBean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.road.show.bean.RoadShowBean;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class RoadDetailModel extends BaseModel<ServiceManager,CacheManager> implements RoadDetailContract.Model {

    @Inject
    public RoadDetailModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<RoadShowBean>> queryRoadDetail(String token, long id, int page_size, long last_id) {
        return mServiceManager.getCommonService().queryRoadDetail(token,id,page_size,last_id);
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
    public Observable<BaseEntity<ReplyIdBean>> reply(String token, int object_type, long object_id, long reply_id, String reply_content, int is_anon) {
        return mServiceManager.getCommonService().reply(token,object_type,object_id,reply_id,reply_content,is_anon);
    }

    @Override
    public Observable<CodeEntity> praise(String token, int object_type, long object_id, int handle_type) {
        return mServiceManager.getCommonService().praise(token,object_type,object_id,handle_type);
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
