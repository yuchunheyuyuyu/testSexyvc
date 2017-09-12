package com.qtin.sexyvc.ui.main.fragconcern;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CreateGroupEntity;
import com.qtin.sexyvc.ui.bean.GroupEntity;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface ConcernContract {
    interface View extends BaseView{
        void querySuccess(int type,GroupEntity groupEntity);
        void deleteSuccess(int type,int position);
        void editSuccess(int type,int position,String group_name);
        void addSuccess(int type,long group_id,String group_name);

        void startRefresh();
        void endRefresh();
    }
    interface Model extends IModel{
        String getToken();
        //关于投资人
        Observable<BaseEntity<GroupEntity>> queryInvestorGroup(String token,long investor_id,long contact_id,int page,int page_size);
        Observable<BaseEntity<CreateGroupEntity>> addInvestorGroup(String token,String group_name);
        Observable<CodeEntity> updateInvestorGroup(String token,long group_id,String group_name,int status);
        //关于投资机构
        Observable<BaseEntity<GroupEntity>> queryFundGroup(String token,int object_type,int page,int page_size);
        Observable<BaseEntity<CreateGroupEntity>> addFundGroup(String token,String group_name,int object_type);
        Observable<CodeEntity> updateFundGroup(String token,long group_id,String group_name,int status,int object_type);
    }
}
