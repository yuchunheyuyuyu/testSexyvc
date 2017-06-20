package com.qtin.sexyvc.ui.user.message.notice.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.ui.user.message.notice.NoticeFrag;

import dagger.Component;

/**
 * Created by ls on 17/4/26.
 */
@FragmentScope
@Component(modules = NoticeFragModule.class,dependencies = AppComponent.class)
public interface NoticeFragComponent {
    void inject(NoticeFrag demoFrag);
}
