package com.qtin.sexyvc.ui.user.message.notice;

import android.content.Intent;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseFragment;
import com.qtin.sexyvc.ui.user.message.notice.di.DaggerNoticeFragComponent;
import com.qtin.sexyvc.ui.user.message.notice.di.NoticeFragModule;

/**
 * Created by ls on 17/4/26.
 */

public class NoticeFrag extends MyBaseFragment<NoticeFragPresent> implements NoticeFragContract.View {
    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerNoticeFragComponent.builder().appComponent(appComponent).noticeFragModule(new NoticeFragModule(this)).build().inject(this);
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
