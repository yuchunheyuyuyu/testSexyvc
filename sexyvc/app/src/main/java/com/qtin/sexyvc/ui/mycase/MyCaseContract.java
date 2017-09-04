package com.qtin.sexyvc.ui.mycase;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.DeleteCaseRequest;
import com.qtin.sexyvc.ui.bean.ListBean;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface MyCaseContract {
    interface View extends BaseView{
        void deleteSuccess(long case_id);
        void querySuccess(ListBean<CaseBean> listBean);

        void showNetErrorView();
        void showContentView();
        void showEmptyView();
    }
    interface Model extends IModel{
        Observable<BaseEntity<ListBean<CaseBean>>> queryMyCase(String token);
        Observable<CodeEntity> deleteCase(DeleteCaseRequest request);
        String getToken();
    }
}
