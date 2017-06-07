package com.qtin.sexyvc.mvp.test.share;

import android.content.Intent;
import android.view.View;
import com.jess.arms.utils.UiUtils;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import butterknife.OnClick;

/**
 * Created by ls on 17/3/2.
 */

public class ShareActivity extends MyBaseActivity {

    private  UMWeb web;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

        String text = "今天公牛主场 VS 勇士的比赛已经完结了，公牛成功守住主场";
        String title = "勇士败给公牛，近两季常规赛首次连败";
        String url = "https://bbs.hupu.com/18625599.html";

        web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setDescription(text);
        web.setThumb(new UMImage(this, R.drawable.umeng_socialize_wechat));  //缩略图
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.test_share_activity;
    }

    @Override
    protected void initData() {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                share(SHARE_MEDIA.SINA);
                break;
            case R.id.button2:
                share(SHARE_MEDIA.QQ);
                break;
            case R.id.button3:
                share(SHARE_MEDIA.QZONE);
                break;
            case R.id.button4:
                share(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.button5:
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
        }
    }

    private void share(SHARE_MEDIA platform){
        new ShareAction(ShareActivity.this)
                .withMedia(web)
                .setPlatform(platform)
                .setCallback(shareListener).share();
    }

    private UMShareListener shareListener=new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            UiUtils.SnackbarText("开始分享");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            UiUtils.SnackbarText("分享onResult");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            UiUtils.SnackbarText("分享onError");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            UiUtils.SnackbarText("分享onCancel");
        }
    };


}
