package com.qtin.sexyvc.ui.subject.list;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.subject.bean.SubjectBean;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface SubjectListContract {
    interface View extends BaseView{
        void startLoadMore();
        void endLoadMore();
        void querySuccess(int need_banner,SubjectBean subjectBean);
    }
    interface Model extends IModel{
        Observable<BaseEntity<SubjectBean>> querySubjectList(long subject_id,int page_size,int need_banner);
    }
}
