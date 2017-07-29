package com.qtin.sexyvc.ui.comment.detail;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.ReplyIdBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.comment.detail.bean.CommentBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface CommentDetailContract {
    interface View extends BaseView{
        void praiseSuccess(int position);
        void replySuccess(int position,long reply_id,String content);
        void dialogWaitLoading();
        void dialogWaitDismiss();
        void querySuccess(long reply_id,CommentBean commentBean);
        void startLoadMore();
        void endLoadMore();
        void showNotExistDialog();
    }

    interface Model extends IModel{
        String getToken();
        Observable<BaseEntity<ReplyIdBean>> reply(String token, int object_type, long object_id, long reply_id, String reply_content);
        Observable<CodeEntity> praise(String token, int object_type, long object_id, int handle_type);
        UserInfoEntity getUserInfo();
        Observable<BaseEntity<CommentBean>> queryCommentDetail(String token, long comment_id,int page_size,long reply_id);
    }
}
