package com.qtin.sexyvc.ui.login.choose;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface ChooseIdentityContract {
    interface View extends BaseView{
        void editSuccess();
    }
    interface Model extends IModel{
        String getToken();
        Observable<CodeEntity> editPosition(String token, int u_auth_type, String u_company, String u_title);
        void saveUsrInfo(UserInfoEntity entity);
    }
}
