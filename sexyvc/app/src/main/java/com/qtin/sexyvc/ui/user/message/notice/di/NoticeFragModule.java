package com.qtin.sexyvc.ui.user.message.notice.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.user.message.notice.NoticeFragContract;
import com.qtin.sexyvc.ui.user.message.notice.NoticeFragModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class NoticeFragModule {
    private NoticeFragContract.View view;

    public NoticeFragModule(NoticeFragContract.View view) {
        this.view = view;
    }
    @FragmentScope
    @Provides
    NoticeFragContract.View provideDemoView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    NoticeFragContract.Model provideDemoModel(NoticeFragModel model){
        return model;
    }
}
