package com.qtin.sexyvc.ui.comment.detail.di;

import com.jess.arms.di.scope.ActivityScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailActivity;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@ActivityScope
@Component(modules = CommentDetailModule.class,dependencies = AppComponent.class)
public interface CommentDetailComponent {
    void inject(CommentDetailActivity activity);
}
