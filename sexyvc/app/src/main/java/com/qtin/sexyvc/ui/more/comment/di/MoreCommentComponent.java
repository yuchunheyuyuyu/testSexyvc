package com.qtin.sexyvc.ui.more.comment.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.more.comment.MoreCommentActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = MoreCommentModule.class,dependencies = AppComponent.class)
public interface MoreCommentComponent {
    void inject(MoreCommentActivity activity);
}
