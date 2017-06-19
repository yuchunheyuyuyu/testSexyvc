package com.qtin.sexyvc.ui.user.message.message.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.user.message.message.MessageFragContract;
import com.qtin.sexyvc.ui.user.message.message.MessageFragModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class MessageFragModule {
    private MessageFragContract.View view;

    public MessageFragModule(MessageFragContract.View view) {
        this.view = view;
    }
    @FragmentScope
    @Provides
    MessageFragContract.View provideDemoView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    MessageFragContract.Model provideDemoModel(MessageFragModel model){
        return model;
    }
}
