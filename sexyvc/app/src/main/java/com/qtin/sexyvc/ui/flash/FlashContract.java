package com.qtin.sexyvc.ui.flash;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.flash.bean.FlashBean;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface FlashContract {
    interface View extends BaseView{
        void querySuccess(boolean pullToRefresh,FlashBean bean);
        void startLoadMore();
        void endLoadMore();
    }
    interface Model extends IModel{
        Observable<BaseEntity<FlashBean>> queryFlashList(long flash_id,int page_size);
    }
}
