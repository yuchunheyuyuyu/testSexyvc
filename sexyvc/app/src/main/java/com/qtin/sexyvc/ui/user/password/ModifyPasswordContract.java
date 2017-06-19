package com.qtin.sexyvc.ui.user.password;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.CodeEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface ModifyPasswordContract {
    interface View extends BaseView{
        void modifySuccess();
    }
    interface Model extends IModel{
        String getToken();
        Observable<CodeEntity> modifyPassword(String token,String old_password,String new_password);
    }
}
