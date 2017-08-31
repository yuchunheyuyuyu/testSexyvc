package com.qtin.sexyvc.ui.user.project.my;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.user.project.my.bean.ProjectEntity;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface MyProjectContract {
    interface View extends BaseView{
        void querySuccess(ProjectEntity entity);
        void requestTypeBack(int type,ArrayList<FilterEntity> list);
        void showNetErrorView();
        void showContentView();
    }
    interface Model extends IModel{
        Observable<BaseEntity<ProjectEntity>> queryMyProject(String token);
        String getToken();
        Observable<Typebean> getType(String type_key);
    }
}
