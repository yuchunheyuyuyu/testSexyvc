package com.qtin.sexyvc.ui.more.object.road.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.more.object.road.FragRoadCommentContract;
import com.qtin.sexyvc.ui.more.object.road.FragRoadCommentModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class FragRoadCommentModule {
    private FragRoadCommentContract.View view;

    public FragRoadCommentModule(FragRoadCommentContract.View view) {
        this.view = view;
    }
    @FragmentScope
    @Provides
    FragRoadCommentContract.View provideDemoView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    FragRoadCommentContract.Model provideDemoModel(FragRoadCommentModel model){
        return model;
    }
}
