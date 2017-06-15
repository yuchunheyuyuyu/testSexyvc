package com.qtin.sexyvc.ui.main.fragmine;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import rx.Observable;
/**
 * Created by ls on 17/4/25.
 */
public interface FragMineContract {
    interface View extends BaseView{
        void requestSuccess(UserInfoEntity entity);
        void requestFail();
    }

    interface Model extends IModel{
        Observable<BaseEntity<UserInfoEntity>>  getUserInfo(String token);
        String getToken();
    }
}
