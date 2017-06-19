package com.qtin.sexyvc.ui.user.message.message.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.message.message.MessageFrag;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = MessageFragModule.class,dependencies = AppComponent.class)
public interface MessageFragComponent {
    void inject(MessageFrag demoFrag);
}
