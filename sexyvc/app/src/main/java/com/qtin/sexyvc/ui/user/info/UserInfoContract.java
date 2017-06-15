package com.qtin.sexyvc.ui.user.info;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.QiniuTokenEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface UserInfoContract {
    interface View extends BaseView{
        void editAvatarSuccess(String avatar);
        void editSexSuccess(int u_gender);
    }
    interface Model extends IModel{
        Observable<CodeEntity> editAvatar(String token,String u_avatar);
        Observable<CodeEntity> editSex(String token,int u_gender);

        Observable<BaseEntity<QiniuTokenEntity>> getQiniuToken(int is_protected);

        String getToken();
    }
}
