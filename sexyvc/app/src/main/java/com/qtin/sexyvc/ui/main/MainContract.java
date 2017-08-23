package com.qtin.sexyvc.ui.main;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.AndroidUpdateBean;
import com.qtin.sexyvc.ui.bean.AppUpdateBean;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface MainContract {
    interface View extends BaseView{
        void queryUpdateSuccess(AppUpdateBean updateBean);

        void requestTypeBack(int type,ArrayList<FilterEntity> list);
        void onCreateSuccess(ProjectBean bean);
        void showProgress(String msg);
        void hideProgress();
    }
    interface Model extends IModel{
        void saveUsrInfo(UserInfoEntity entity);
        String getToken();
        UserInfoEntity getUserInfo();
        Observable<BaseEntity<UserInfoEntity>>  getUserInfo(String token);

        Observable<BaseEntity<AndroidUpdateBean>> queryUpdate();

        //这里写Model中公开的方法，在present调用
        Observable<Typebean> getType(String type_key);
        Observable<CodeEntity> createProject(ProjectBean request);
        void  updateProjectState();
    }
}
