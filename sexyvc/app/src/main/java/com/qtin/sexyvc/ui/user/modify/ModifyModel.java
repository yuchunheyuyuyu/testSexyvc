package com.qtin.sexyvc.ui.user.modify;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import com.qtin.sexyvc.mvp.model.api.cache.CacheManager;
import com.qtin.sexyvc.mvp.model.api.service.ServiceManager;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.UserEntity;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
public class ModifyModel extends BaseModel<ServiceManager,CacheManager> implements ModifyContract.Model {

    @Inject
    public ModifyModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public String getToken() {
        List<UserEntity> list=mCacheManager.getDaoSession().getUserEntityDao().queryBuilder().build().list();
        if(list!=null&&!list.isEmpty()){
            return list.get(0).getU_token();
        }
        return "";
    }

    @Override
    public Observable<CodeEntity> editNick(String token, String u_nickname) {
        return mServiceManager.getCommonService().editNick(token,u_nickname);
    }

    @Override
    public Observable<CodeEntity> editSignature(String token, String u_signature) {
        return mServiceManager.getCommonService().editSignature(token,u_signature);
    }

    @Override
    public Observable<CodeEntity> editEmail(String token, String u_email, String u_backup_email) {
        return mServiceManager.getCommonService().editEmail(token,u_email,u_backup_email);
    }

    @Override
    public Observable<CodeEntity> editMobile(String token, String u_backup_phone) {
        return mServiceManager.getCommonService().editMobile(token,u_backup_phone);
    }

    @Override
    public Observable<CodeEntity> editContactPhone(String token, long contact_id, String phone, String backup_phone) {
        return mServiceManager.getCommonService().editContactPhone(token,contact_id,phone,backup_phone);
    }

    @Override
    public Observable<CodeEntity> editContactEmail(String token, long contact_id, String email, String backup_email) {
        return mServiceManager.getCommonService().editContactEmail(token,contact_id,email,backup_email);
    }

    @Override
    public Observable<CodeEntity> editContactWechat(String token, long contact_id, String wechat) {
        return mServiceManager.getCommonService().editContactWechat(token,contact_id,wechat);
    }

    @Override
    public Observable<CodeEntity> editContactemark(String token, long contact_id, String remark) {
        return mServiceManager.getCommonService().editContactemark(token,contact_id,remark);
    }

    @Override
    public Observable<CodeEntity> report(String token, int report_type, String content) {
        return mServiceManager.getCommonService().report(token,report_type,content);
    }

    @Override
    public Observable<CodeEntity> editUCompany(String token, String u_company) {
        return mServiceManager.getCommonService().editUCompany(token,u_company);
    }

    @Override
    public Observable<CodeEntity> editUTitle(String token, String u_title) {
        return mServiceManager.getCommonService().editUTitle(token,u_title);
    }
}
