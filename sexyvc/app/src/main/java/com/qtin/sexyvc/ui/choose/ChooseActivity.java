package com.qtin.sexyvc.ui.choose;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import butterknife.BindView;

/**
 * Created by ls on 17/6/29.
 */

public class ChooseActivity extends MyBaseActivity {

    @BindView(R.id.ivBackGround)
    ImageView ivBackGround;
    @BindView(R.id.ivRoadComment)
    ImageView ivRoadComment;
    @BindView(R.id.ivEditComment)
    ImageView ivEditComment;
    @BindView(R.id.switchContainer)
    RelativeLayout switchContainer;
    @BindView(R.id.ivChoose)
    ImageView ivChoose;
    @BindView(R.id.tvRoadComment)
    TextView tvRoadComment;
    @BindView(R.id.tvEditComment)
    TextView tvEditComment;
    @BindView(R.id.tvChooseTitle)
    TextView tvChooseTitle;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.option_comment_popupwindow;
    }

    @Override
    protected void initData() {

    }
}
