package com.qtin.sexyvc.ui.follow.set;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ChangeFundGroupRequest;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CreateGroupEntity;
import com.qtin.sexyvc.ui.bean.GroupEntity;
import com.qtin.sexyvc.ui.request.ChangeContactGroupRequest;
import com.qtin.sexyvc.ui.request.ChangeInvestorGroupRequest;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface SetGroupContract {
    interface View extends BaseView{

        void querySuccess(GroupEntity entity);
        void addSuccess(long group_id,String group_name);
        void changeSuccess();

        void startRefresh(String msg);
        void endRefresh();
    }
    interface Model extends IModel{

        String getToken();
        //投资人相关
        Observable<BaseEntity<CreateGroupEntity>> addInvestorGroup(String token, String group_name);
        Observable<CodeEntity> changeGroup(ChangeInvestorGroupRequest request);
        Observable<CodeEntity> changeContactGroup(ChangeContactGroupRequest request);
        Observable<BaseEntity<GroupEntity>> queryInvestorGroup(String token, long investor_id, long contact_id,int page, int page_size);

        //投资机构相关
        Observable<BaseEntity<GroupEntity>> queryFundGroup(String token,int object_type,long object_id,int page,int page_size);
        Observable<BaseEntity<CreateGroupEntity>> addFundGroup(String token,String group_name,int object_type);
        Observable<CodeEntity> changeFundGroup(ChangeFundGroupRequest request);
    }
}
