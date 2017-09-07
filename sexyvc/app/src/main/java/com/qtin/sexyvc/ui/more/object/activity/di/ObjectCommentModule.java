package com.qtin.sexyvc.ui.more.object.activity.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.more.object.activity.ObjectCommentContract;
import com.qtin.sexyvc.ui.more.object.activity.ObjectCommentModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ObjectCommentModule {
    private ObjectCommentContract.View view;

    public ObjectCommentModule(ObjectCommentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ObjectCommentContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ObjectCommentContract.Model provideDemoModel(ObjectCommentModel model){
        return model;
    }
}
