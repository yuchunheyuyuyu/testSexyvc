package com.qtin.sexyvc.ui.review.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.ui.review.ReviewContract;
import com.qtin.sexyvc.ui.review.ReviewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class ReviewModule {
    private ReviewContract.View view;

    public ReviewModule(ReviewContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ReviewContract.View provideDemoView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ReviewContract.Model provideDemoModel(ReviewModel model){
        return model;
    }
}
