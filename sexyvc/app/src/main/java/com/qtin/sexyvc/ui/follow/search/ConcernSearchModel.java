package com.qtin.sexyvc.ui.follow.search;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.ConcernListEntity;
import com.qtin.sexyvc.ui.bean.ConcernListEntityDao;
import com.qtin.sexyvc.ui.bean.UserEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class ConcernSearchModel extends BaseModel<ServiceManager,CacheManager> implements ConcernSearchContract.Model {

    @Inject
    public ConcernSearchModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<ConcernEntity>> searchConcern(String token, String keyword) {
        return mServiceManager.getCommonService().searchConcern(token,keyword);
    }

    @Override
    public void insertConcern(ConcernListEntity listEntity) {
        listEntity.setLocalTime(System.currentTimeMillis());

        List<ConcernListEntity> list=mCacheManager.getDaoSession().getConcernListEntityDao().queryBuilder().build().list();
        if(list!=null){
            for(int i=0;i<list.size();i++){
                if(list.get(i).getContact_id()==listEntity.getContact_id()){
                    mCacheManager.getDaoSession().getConcernListEntityDao().delete(list.get(i));
                    break;
                }
            }
        }
        mCacheManager.getDaoSession().getConcernListEntityDao().insert(listEntity);
    }

    @Override
    public List<ConcernListEntity> queryLocalHistory() {
        List<ConcernListEntity> list=mCacheManager.getDaoSession().getConcernListEntityDao().queryBuilder().orderDesc(ConcernListEntityDao.Properties.LocalTime).build().list();
        return list;
    }

    @Override
    public void clearHistory() {
        mCacheManager.getDaoSession().getConcernListEntityDao().deleteAll();
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
