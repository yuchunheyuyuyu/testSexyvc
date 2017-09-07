package com.qtin.sexyvc.ui.more.object.activity.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.more.object.activity.ObjectCommentActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = ObjectCommentModule.class,dependencies = AppComponent.class)
public interface ObjectCommentComponent {
    void inject(ObjectCommentActivity activity);
}
