package com.qtin.sexyvc.ui.follow.set;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CreateGroupEntity;
import com.qtin.sexyvc.ui.bean.GroupEntity;
import com.qtin.sexyvc.ui.request.ChangeGroupRequest;

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
        Observable<BaseEntity<CreateGroupEntity>> addInvestorGroup(String token, String group_name);
        Observable<CodeEntity> changeGroup(ChangeGroupRequest request);
        Observable<BaseEntity<GroupEntity>> queryInvestorGroup(String token, long investor_id, int page, int page_size);
    }
}
