package com.qtin.sexyvc.ui.follow.set;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CreateGroupEntity;
import com.qtin.sexyvc.ui.bean.GroupEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.request.ChangeGroupRequest;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class SetGroupModel extends BaseModel<ServiceManager,CacheManager> implements SetGroupContract.Model {

    @Inject
    public SetGroupModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
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
    public Observable<BaseEntity<CreateGroupEntity>> addInvestorGroup(String token, String group_name) {
        return mServiceManager.getCommonService().addInvestorGroup(token,group_name);
    }

    @Override
    public Observable<CodeEntity> changeGroup(ChangeGroupRequest request) {
        return mServiceManager.getCommonService().changeGroup(request);
    }

    @Override
    public Observable<BaseEntity<GroupEntity>> queryInvestorGroup(String token, long investor_id, int page, int page_size) {
        return mServiceManager.getCommonService().queryInvestorGroup(token,investor_id,page,page_size);
    }
}
