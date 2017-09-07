package com.qtin.sexyvc.ui.mycase.add;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CaseBean;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface AddCaseContract {
    interface View extends BaseView{
        void onCreateSuccess(CaseBean caseBean);
        void uploadPhotoSuccess(String imageUrl);


        void showProgress(String msg);
        void hideProgress();
    }
    interface Model extends IModel{
        Observable<BaseEntity<CaseBean>> addMyCase(String token,String case_name,String case_logo);
        String getToken();
        Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(int is_protected);
    }
}
