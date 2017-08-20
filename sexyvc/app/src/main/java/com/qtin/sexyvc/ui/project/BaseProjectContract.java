package com.qtin.sexyvc.ui.project;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.Typebean;
import java.util.ArrayList;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface BaseProjectContract {
    interface View extends BaseView{
        void requestTypeBack(int type,ArrayList<FilterEntity> list);
        void onCreateSuccess(ProjectBean bean);
        void showProgress(String msg);
        void hideProgress();
    }
    interface Model extends IModel{
        //这里写Model中公开的方法，在present调用
        Observable<Typebean> getType(String type_key);
        Observable<CodeEntity> createProject(ProjectBean request);
        String getToken();
        void  updateProjectState();
    }
}
