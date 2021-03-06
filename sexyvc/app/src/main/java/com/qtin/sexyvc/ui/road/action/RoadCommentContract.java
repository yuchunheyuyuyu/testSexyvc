package com.qtin.sexyvc.ui.road.action;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.BaseEntity;
import com.qtin.sexyvc.ui.bean.BaseListEntity;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import com.qtin.sexyvc.ui.bean.CommonBean;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.Typebean;
import com.qtin.sexyvc.ui.bean.UserInfoEntity;
import com.qtin.sexyvc.ui.road.action.bean.QuestionBean;
import com.qtin.sexyvc.ui.road.action.bean.RoadRequest;
import com.qtin.sexyvc.ui.user.project.my.bean.ProjectEntity;
import java.util.ArrayList;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */

public interface RoadCommentContract {
    interface View extends BaseView{
        void queryFail();
        void querySuccess(BaseListEntity<QuestionBean> listEntity);
        void startLoad(String msg);
        void endLoad();
        void onUploadAnswersSuccess();
        void queryNormalQuestionsSuccess(CommonBean commonBean);

        void requestTypeBack(int type,ArrayList<FilterEntity> list);

        void queryProjectSuccess(ProjectEntity entity);
    }
    interface Model extends IModel{
        Observable<BaseListEntity<QuestionBean>> queryRoadQuestion(String token);
        String getToken();
        Observable<CodeEntity> uploadAnswers(RoadRequest request);
        Observable<BaseEntity<CommonBean>> queryNormalQuestion();

        void changeRoadStatus();
        Observable<Typebean> getType(String type_key);
        Observable<BaseEntity<ProjectEntity>> queryMyProject(String token);
        UserInfoEntity getUserInfo();
    }
}
