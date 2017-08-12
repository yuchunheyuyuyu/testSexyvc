package com.qtin.sexyvc.ui.web;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.PageBean;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface WebContract {
    interface View extends BaseView{
        void querySuccess(PageBean pageBean);
        void startRefresh(String msg);
        void endRefresh();
    }
    interface Model extends IModel{
        Observable<BaseEntity<PageBean>> queryPage(String alias_name);
    }
}
