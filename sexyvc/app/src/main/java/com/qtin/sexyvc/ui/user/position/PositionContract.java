package com.qtin.sexyvc.ui.user.position;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface PositionContract {
    interface View extends BaseView{
        void editAuthTypeSuccess(int u_auth_type);
        void cancleAuthSuccess();

        void startRefresh(String msg);
        void endRefresh();
    }
    interface Model extends IModel{
        String getToken();
        Observable<CodeEntity> editAuthType(String token,int u_auth_type);
        void saveUsrInfo(UserInfoEntity entity);
        Observable<CodeEntity> cancelAuth(String token);
    }
}
