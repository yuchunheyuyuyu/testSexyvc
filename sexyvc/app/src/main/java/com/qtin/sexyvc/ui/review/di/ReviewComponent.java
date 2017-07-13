package com.qtin.sexyvc.ui.review.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.review.ReviewActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ReviewModule.class,dependencies = AppComponent.class)
public interface ReviewComponent {
    void inject(ReviewActivity activity);
}
