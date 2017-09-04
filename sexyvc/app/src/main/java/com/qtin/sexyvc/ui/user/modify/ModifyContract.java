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
        //个人中心
        Observable<CodeEntity> editNick(String token,String u_nickname);
        Observable<CodeEntity> editSignature(String token,String u_signature);
        Observable<CodeEntity> editEmail(String token,String u_email,String u_backup_email);
        Observable<CodeEntity> editMobile(String token,String u_backup_phone);

        //投资联系人
        Observable<CodeEntity> editContactPhone(String token,long contact_id,String phone,String backup_phone);
        Observable<CodeEntity> editContactEmail(String token,long contact_id,String email,String backup_email);
        Observable<CodeEntity> editContactWechat(String token,long contact_id,String wechat);
        Observable<CodeEntity> editContactemark(String token,long contact_id,String remark);

        Observable<CodeEntity> report(String token,int report_type,String content);

        Observable<CodeEntity> editUCompany(String token,String u_company);
        Observable<CodeEntity> editUTitle(String token,String u_title);
    }
}
