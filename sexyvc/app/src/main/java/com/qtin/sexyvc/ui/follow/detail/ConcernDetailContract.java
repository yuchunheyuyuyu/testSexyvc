package com.qtin.sexyvc.ui.follow.detail;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ContactBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface ConcernDetailContract {
    interface View extends BaseView{
        void querySuccess(ContactBean bean);
    }
    interface Model extends IModel{
        Observable<BaseEntity<ContactBean>> queryContactDetail(String token,long contact_id);
        String getToken();
    }
}
