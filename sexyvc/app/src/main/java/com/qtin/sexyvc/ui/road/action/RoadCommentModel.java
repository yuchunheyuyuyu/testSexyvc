package com.qtin.sexyvc.ui.road.action;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CommonBean;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.road.action.bean.QuestionBean;
import com.qtin.sexyvc.ui.road.action.bean.RoadRequest;
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

    @Override
    public void changeRoadStatus() {
        List<UserInfoEntity> list=mCacheManager.getDaoSession().getUserInfoEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            list.get(0).setHas_roadshow(1);
            mCacheManager.getDaoSession().getUserInfoEntityDao().update(list.get(0));
        }
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
    public Observable<BaseEntity<ProjectEntity>> queryMyProject(String token) {
        return mServiceManager.getCommonService().queryMyProject(token);
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
