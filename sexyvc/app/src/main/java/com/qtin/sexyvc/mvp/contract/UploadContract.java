package com.qtin.sexyvc.mvp.contract;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.mvp.model.entity.BaseEntity;
import com.qtin.sexyvc.mvp.model.entity.QiniuTokenEntity;
import rx.Observable;

/**
 * Created by ls on 17/3/1.
 */
public interface UploadContract {
    interface View extends BaseView{
        void showProgress(int percent);
    }
    interface Model extends IModel{
        Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(String url);
    }
}
