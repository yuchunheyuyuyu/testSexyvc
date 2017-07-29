package com.qtin.sexyvc.ui.road;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CommonBean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.road.bean.QuestionBean;
import com.qtin.sexyvc.ui.road.bean.RoadRequest;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class RoadCommentModel extends BaseModel<ServiceManager,CacheManager> implements RoadCommentContract.Model {

    @Inject
    public RoadCommentModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseListEntity<QuestionBean>> queryRoadQuestion(String token) {
        return mServiceManager.getCommonService().queryRoadQuestion(token);
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
    public Observable<CodeEntity> uploadAnswers(RoadRequest request) {
        return mServiceManager.getCommonService().uploadAnswers(request);
    }

    @Override
    public Observable<BaseEntity<CommonBean>> queryNormalQuestion() {
        return mServiceManager.getCommonService().queryNormalQuestion();
    }
}
