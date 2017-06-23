package com.qtin.sexyvc.ui.subject.list;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.subject.bean.SubjectBean;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class SubjectListModel extends BaseModel<ServiceManager,CacheManager> implements SubjectListContract.Model {

    @Inject
    public SubjectListModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<BaseEntity<SubjectBean>> querySubjectList(long subject_id, int page_size, int need_banner) {
        return mServiceManager.getCommonService().querySubjectList(subject_id,page_size,need_banner);
    }
}
