package com.qtin.sexyvc.ui.more.comment.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.more.comment.MoreCommentContract;
import com.qtin.sexyvc.ui.more.comment.MoreCommentModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class MoreCommentModule {
    private MoreCommentContract.View view;

    public MoreCommentModule(MoreCommentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MoreCommentContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    MoreCommentContract.Model provideDemoModel(MoreCommentModel model){
        return model;
    }
}
