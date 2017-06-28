package com.qtin.sexyvc.ui.comment.list.frag.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.comment.list.frag.CommentLastFrag;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = CommentLastModule.class,dependencies = AppComponent.class)
public interface CommentLastComponent {
    void inject(CommentLastFrag demoFrag);
}
