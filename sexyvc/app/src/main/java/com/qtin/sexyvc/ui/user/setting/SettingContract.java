package com.qtin.sexyvc.ui.user.setting;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

/**
 * Created by ls on 17/4/26.
 */

public interface SettingContract {
    interface View extends BaseView{
        void logoutSuccess();
    }
    interface Model extends IModel{
        void logout();
    }
}
