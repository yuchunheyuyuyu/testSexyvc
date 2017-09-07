package com.qtin.sexyvc.ui.comment.chosen.frag.di;

import com.jess.arms.di.scope.FragmentScope;
import com.qtin.sexyvc.ui.comment.chosen.frag.CommentChosenContract;
import com.qtin.sexyvc.ui.comment.chosen.frag.CommentChosenModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ls on 17/4/26.
 */
@Module
public class CommentChosenModule {
    private CommentChosenContract.View view;

    public CommentChosenModule(CommentChosenContract.View view) {
        this.view = view;
    }
    @FragmentScope
    @Provides
    CommentChosenContract.View provideDemoView(){
        return this.view;
    }

    @FragmentScope
    @Provides
    CommentChosenContract.Model provideDemoModel(CommentChosenModel model){
        return model;
    }
}
