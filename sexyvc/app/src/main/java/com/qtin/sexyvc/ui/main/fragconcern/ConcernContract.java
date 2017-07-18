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
        void querySuccess(GroupEntity groupEntity);
        void deleteSuccess(int position);
        void editSuccess(int position,String group_name);
        void addSuccess(long group_id,String group_name);

        void startRefresh();
        void endRefresh();
    }
    interface Model extends IModel{
        String getToken();
        Observable<BaseEntity<GroupEntity>> queryInvestorGroup(String token,long investor_id,long contact_id,int page,int page_size);
        Observable<BaseEntity<CreateGroupEntity>> addInvestorGroup(String token,String group_name);
        Observable<CodeEntity> updateInvestorGroup(String token,long group_id,String group_name,int status);
    }
}
