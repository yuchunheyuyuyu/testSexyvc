package com.qtin.sexyvc.ui.main.fragmine;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.InfluencyBean;
import com.qtin.sexyvc.ui.bean.UnReadBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

import rx.Observable;
/**
 * Created by ls on 17/4/25.
 */
public interface FragMineContract {
    interface View extends BaseView{
        void requestSuccess(UserInfoEntity entity);
        void requestFail();
        void queryUnReadSuccess(UnReadBean unReadBean);
        void queryInfluencySuccess(InfluencyBean influencyBean);
    }

    interface Model extends IModel{
        Observable<BaseEntity<UserInfoEntity>>  getUserInfo(String token);
        String getToken();
        void saveUsrInfo(UserInfoEntity entity);
        Observable<BaseEntity<UnReadBean>> queryUnRead(String token);
        Observable<BaseEntity<InfluencyBean>> queryInfluency(String token);
    }
}
