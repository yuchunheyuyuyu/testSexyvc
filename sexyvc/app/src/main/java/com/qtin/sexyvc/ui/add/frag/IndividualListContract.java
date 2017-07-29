package com.qtin.sexyvc.ui.add.frag;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.ConcernEntity;
import com.qtin.sexyvc.ui.bean.LastBrowerBean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.investor.bean.CallBackBean;
import java.util.List;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface IndividualListContract {
    interface View extends BaseView{
        void startLoadMore();
        void endLoadMore();
        void querySuccess(ConcernEntity entity);
        void queryDetailSuccess(CallBackBean backBean);
        void startRefresh(String msg);
        void endRefresh();

    }
    interface Model extends IModel{
        Observable<BaseEntity<ConcernEntity>> queryGroupDetail(String token, long group_id, int page, int page_size);
        String getToken();
        List<LastBrowerBean> queryLastBrowers();
        Observable<BaseEntity<CallBackBean>> queryInvestorDetail(String token, long investor_id, long comment_id,int page_size);
        UserInfoEntity getUserInfo();

    }
}
