package com.qtin.sexyvc.ui.comment.list.frag;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.comment.list.frag.bean.CommentItemsBean;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
public class CommentLastModel extends BaseModel<ServiceManager,CacheManager> implements CommentLastContract.Model {

    @Inject
    public CommentLastModel(ServiceManager serviceManager, CacheManager cacheManager) {
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
    public Observable<BaseEntity<CommentItemsBean>> queryCommentList(String token, int page, int page_size, int hot_comment) {
        return mServiceManager.getCommonService().queryCommentList(token,page,page_size,hot_comment);
    }
}
