package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Context;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import java.util.ArrayList;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface FragInvestorContract {

    interface View extends BaseView{
        //这里写view中公开出去的方法，供present调用
        Context getContext();
        void dataCallback(ArrayList<InvestorEntity> data);
        void requestTypeBack(int type,ArrayList<FilterEntity> list);
    }
    interface Model extends IModel{
        //这里写Model中公开的方法，在present调用
        Observable<BaseListEntity<FilterEntity>> getType(String type_key);
    }
}
