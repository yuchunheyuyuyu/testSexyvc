package com.qtin.sexyvc.ui.comment.chosen.detail;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.HomeCommentBean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class ChosenDetailModel extends BaseModel<ServiceManager,CacheManager> implements ChosenDetailContract.Model {

    @Inject
    public ChosenDetailModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<HomeCommentBean>> queryTopicDetail(String token,long topic_id, int page, int page_size) {
        return mServiceManager.getCommonService().queryTopicDetail(token,topic_id,page,page_size);
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
