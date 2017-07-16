package com.qtin.sexyvc.ui.road.success;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;

/**
 * Created by ls on 17/4/26.
 */

public interface RoadSuccessContract {
    interface View extends BaseView{

    }
    interface Model extends IModel{
        UserInfoEntity getUserInfo();
    }
}
