package com.qtin.sexyvc.ui.comment.chosen.frag.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.comment.chosen.frag.CommentChosenFrag;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = CommentChosenModule.class,dependencies = AppComponent.class)
public interface CommentChosenComponent {
    void inject(CommentChosenFrag demoFrag);
}
