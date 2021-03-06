package com.qtin.sexyvc.ui.review;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CommentIdBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface ReviewContract {
    interface View extends BaseView{
        void onCommentSuccess(long comment_id,String comment_title);
        void onAppendSuccess();
    }
    interface Model extends IModel{
        String getToken();
        Observable<BaseEntity<CommentIdBean>> commentInvestor(String token, String title, String content, long investor_id, long fund_id, int is_anon);
        Observable<CodeEntity> appendInvestor(String token,String content,long comment_id);
        void changeCommentStatus();
    }
}
