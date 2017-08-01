package com.qtin.sexyvc.ui.main;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.AndroidUpdateBean;
import com.qtin.sexyvc.ui.bean.AppUpdateBean;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface MainContract {
    interface View extends BaseView{
        void queryUpdateSuccess(AppUpdateBean updateBean);
    }
    interface Model extends IModel{
        void saveUsrInfo(UserInfoEntity entity);
        String getToken();
        UserInfoEntity getUserInfo();

        Observable<BaseEntity<AndroidUpdateBean>> queryUpdate();
    }
}
