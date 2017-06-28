package com.qtin.sexyvc.ui.comment.list.frag.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.comment.list.frag.CommentLastContract;
import com.qtin.sexyvc.ui.comment.list.frag.CommentLastModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class CommentLastModule {
    private CommentLastContract.View view;

    public CommentLastModule(CommentLastContract.View view) {
        this.view = view;
    }
    @FragmentScope
    @Provides
    CommentLastContract.View provideDemoView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    CommentLastContract.Model provideDemoModel(CommentLastModel model){
        return model;
    }
}
