package com.qtin.sexyvc.ui.comment.list.frag;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.comment.list.frag.bean.CommentItemsBean;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface CommentLastContract {
    interface View extends BaseView{
        void querySuccess(CommentItemsBean bean);
        void startLoadMore();
        void endLoadMore();
    }
    interface Model extends IModel{
        String getToken();
        Observable<BaseEntity<CommentItemsBean>> queryCommentList(String token,int page,int page_size,int hot_comment);
    }
}
