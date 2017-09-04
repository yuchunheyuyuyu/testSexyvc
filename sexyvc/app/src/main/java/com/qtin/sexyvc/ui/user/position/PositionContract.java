package com.qtin.sexyvc.ui.user.position;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface PositionContract {
    interface View extends BaseView{
        void editAuthTypeSuccess(int u_auth_type);
    }
    interface Model extends IModel{
        String getToken();
        Observable<CodeEntity> editAuthType(String token,int u_auth_type);
    }
}
