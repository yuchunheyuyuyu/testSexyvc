package com.qtin.sexyvc.ui.search.action;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.KeyWordBean;

import java.util.ArrayList;

/**
 * Created by ls on 17/4/26.
 */

public interface SearchActionContract {
    interface View extends BaseView{
        void querySuccess(ArrayList<KeyWordBean> keys);
    }
    interface Model extends IModel{
        ArrayList<KeyWordBean> getKeys();
        void insertKey(KeyWordBean bean);
        void delete();
    }
}
