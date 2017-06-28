package com.qtin.sexyvc.ui.comment.detail;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.ReplyIdBean;
import com.qtin.sexyvc.ui.bean.UserEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.comment.detail.bean.CommentBean;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class CommentDetailModel extends BaseModel<ServiceManager,CacheManager> implements CommentDetailContract.Model {

    @Inject
    public CommentDetailModel(ServiceManager serviceManager, CacheManager cacheManager) {
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
    public Observable<BaseEntity<ReplyIdBean>> reply(String token, int object_type, long object_id, long reply_id, String reply_content) {
        return mServiceManager.getCommonService().reply(token,object_type,object_id,reply_id,reply_content);
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

    @Override
    public Observable<BaseEntity<CommentBean>> queryCommentDetail(String token, long comment_id, int page_size, long reply_id) {
        return mServiceManager.getCommonService().queryCommentDetail(token,comment_id,page_size,reply_id);
    }
}
