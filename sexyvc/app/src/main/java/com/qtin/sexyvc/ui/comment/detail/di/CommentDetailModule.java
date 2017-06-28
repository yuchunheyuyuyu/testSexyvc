package com.qtin.sexyvc.ui.comment.detail.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailContract;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class CommentDetailModule {
    private CommentDetailContract.View view;

    public CommentDetailModule(CommentDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommentDetailContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    CommentDetailContract.Model provideDemoModel(CommentDetailModel model){
        return model;
    }
}
