package com.qtin.sexyvc.ui.user.photo;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface PhotoContract {
    interface View extends BaseView{
        void uploadSuccess(String url);
    }
    interface Model extends IModel{
        Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(int is_protected);
        String getToken();
        Observable<CodeEntity> uploadVertifyPhoto(String token,String img_url);
    }
}
