package com.qtin.sexyvc.ui.user.project.add;

import android.content.Context;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import com.qtin.sexyvc.ui.bean.Typebean;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface AddProjectContract {
    interface View extends BaseView{
        //这里写view中公开出去的方法，供present调用
        Context getContext();
        void requestTypeBack(int type,ArrayList<FilterEntity> list);
        void onEditSuccess(ProjectBean bean);
        void onCreateSuccess(ProjectBean bean);
        void uploadPhotoSuccess(String imageUrl);

        void showProgress(String msg);
        void hideProgress();
    }
    interface Model extends IModel{
        //这里写Model中公开的方法，在present调用
        Observable<Typebean> getType(String type_key);
        Observable<CodeEntity> createProject(ProjectBean request);
        Observable<CodeEntity> editProject(ProjectBean request);
        String getToken();
        Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(int is_protected);
        void  updateProjectState();
    }
}
