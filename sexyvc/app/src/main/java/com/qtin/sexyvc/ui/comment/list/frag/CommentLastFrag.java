package com.qtin.sexyvc.ui.comment.list.frag;

import android.content.Intent;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.comment.list.frag.di.CommentLastModule;
import com.qtin.sexyvc.ui.comment.list.frag.di.DaggerCommentLastComponent;

/**
 * Created by ls on 17/4/26.
 */

public class CommentLastFrag extends MyBaseFragment<CommentLastPresent> implements CommentLastContract.View {
    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerCommentLastComponent.builder().appComponent(appComponent).commentLastModule(new CommentLastModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.swipe_recycleview;
    }

    @Override
    protected void init() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
