package com.qtin.sexyvc.ui.user.influency;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.InfluencyBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface InfluencyContract {
    interface View extends BaseView{
        void queryInfluencySuccess(InfluencyBean influencyBean);
    }
    interface Model extends IModel{
        Observable<BaseEntity<InfluencyBean>> queryInfluency(String token);
        String getToken();
    }
}
