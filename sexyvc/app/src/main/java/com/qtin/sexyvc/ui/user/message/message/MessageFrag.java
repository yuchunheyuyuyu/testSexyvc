package com.qtin.sexyvc.ui.user.message.message;

import android.content.Intent;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.user.message.message.di.DaggerMessageFragComponent;
import com.qtin.sexyvc.ui.user.message.message.di.MessageFragModule;

/**
 * Created by ls on 17/4/26.
 */

public class MessageFrag extends MyBaseFragment<MessageFragPresent> implements MessageFragContract.View {
    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMessageFragComponent.builder().appComponent(appComponent).messageFragModule(new MessageFragModule(this)).build().inject(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.message_frag;
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
