package com.qtin.sexyvc.ui.user.modify;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.CodeEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface ModifyContract {
    interface View extends BaseView{
        void editSuccess();
    }
    interface Model extends IModel{
        String getToken();
        Observable<CodeEntity> editNick(String token,String u_nickname);
        Observable<CodeEntity> editSignature(String token,String u_signature);
        Observable<CodeEntity> editEmail(String token,String u_email,String u_backup_email);
        Observable<CodeEntity> editMobile(String token,String u_backup_phone);
    }
}
