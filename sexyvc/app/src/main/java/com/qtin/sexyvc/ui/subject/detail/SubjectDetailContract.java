package com.qtin.sexyvc.ui.subject.detail;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.ReplyIdBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.subject.bean.DetailBean;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface SubjectDetailContract {
    interface View extends BaseView{
        void startLoadMore();
        void endLoadMore();
        void querySuccess(long reply_id,DetailBean bean);

        void praiseSuccess(int position);
        void replySuccess(int position,long reply_id,String content,int is_anon);
        void dialogWaitLoading();
        void dialogWaitDismiss();

        void showNotExistDialog();
        void showNetErrorView();
        void showContentView();
        void showEmptyView();
    }
    interface Model extends IModel{
        String getToken();
        Observable<BaseEntity<DetailBean>> querySubjectDetail(String token,long subject_id,int page_size,long reply_id);
        Observable<BaseEntity<ReplyIdBean>> reply(String token,int object_type,long object_id,long reply_id,String reply_content,int is_anon);
        Observable<CodeEntity> praise(String token,int object_type,long object_id,int handle_type);
        UserInfoEntity getUserInfo();
    }
}
