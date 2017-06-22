package com.qtin.sexyvc.ui.concern.list;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.qtin.sexyvc.ui.bean.CodeEntity;
import rx.Observable;

/**
 * Created by ls on 17/4/26.
 */
public interface ConcernListContract {
    interface View extends BaseView{
        
    }
    interface Model extends IModel{
        Observable<CodeEntity> queryGroupDetail(String token,long group_id);
    }
}
