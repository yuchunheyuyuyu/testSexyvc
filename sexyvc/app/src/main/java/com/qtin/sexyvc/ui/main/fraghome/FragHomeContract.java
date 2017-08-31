package com.qtin.sexyvc.ui.main.fraghome;

import android.content.Context;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.main.fraghome.bean.HomeBean;
import com.qtin.sexyvc.ui.main.fraghome.entity.HomeInterface;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface FragHomeContract {
    interface View extends BaseView{
        Context getContext();
        void dataCallback(ArrayList<HomeInterface> data);
        void showNetErrorView();
        void showContentView();
    }

    interface Model extends IModel{
        Observable<BaseEntity<HomeBean>> queryHome();
    }
}
