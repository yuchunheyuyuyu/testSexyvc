package com.qtin.sexyvc.ui.main.fragInvestor;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

/**
 * Created by ls on 17/4/26.
 */

public interface FragInvestorContract {

    interface View extends BaseView{
        //这里写view中公开出去的方法，供present调用
    }
    interface Model extends IModel{
        //这里写Model中公开的方法，在present调用
    }
}
