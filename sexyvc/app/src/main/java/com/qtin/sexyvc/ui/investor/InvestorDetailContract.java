package com.qtin.sexyvc.ui.investor;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.LastBrowerBean;
import com.qtin.sexyvc.ui.bean.ProjectBean;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import com.qtin.sexyvc.ui.request.FollowRequest;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface InvestorDetailContract {
    interface View extends BaseView{
        void querySuccess(CallBackBean backBean);
        void followSuccess();
        void startRefresh(String msg);
        void endRefresh();
        void cancleSuccess(long investor_id);

        void requestTypeBack(int type,ArrayList<FilterEntity> list);
        void onCreateSuccess(ProjectBean bean);
    }
    interface Model extends IModel{
        Observable<CodeEntity> unFollowInvestor(String token,long investor_id);
        String getToken();
        UserInfoEntity getUserInfo();
        Observable<CodeEntity> followInvestor(FollowRequest entity);
        Observable<BaseEntity<CallBackBean>> queryInvestorDetail(String token,long investor_id,long comment_id,int page_size,int auth_state);
        void insertLastBrower(LastBrowerBean bean);

        Observable<Typebean> getType(String type_key);
        Observable<CodeEntity> createProject(ProjectBean request);
        void  updateProjectState();
    }
}
