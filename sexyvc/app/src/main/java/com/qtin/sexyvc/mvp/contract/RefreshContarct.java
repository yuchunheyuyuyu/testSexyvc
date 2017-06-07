package com.qtin.sexyvc.mvp.contract;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.mvp.model.entity.User;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.util.List;
import rx.Observable;

/**
 * Created by ls on 17/2/28.
 */

public interface RefreshContarct {
    interface View extends BaseView{
        void setAdapter(DefaultAdapter adapter);
        //申请权限
        RxPermissions getRxPermissions();
        void startLoadMore();
        void endLoadMore();
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel{
        Observable<List<User>> getUser(int lastIdQueried, boolean update);
    }
}
