package com.qtin.sexyvc.ui.more;

import android.widget.FrameLayout;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.more.object.road.FragRoadComment;
import com.qtin.sexyvc.utils.ConstantUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/9/8.
 */
public class UnAuthCommentActivity extends MyBaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    private int type;
    private long contentId;
    private static final int auth_state=0;

    @Override
    protected boolean isContaineFragment() {
        return true;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.un_auth_comment_activity;
    }

    @Override
    protected void initData() {
        type=getIntent().getExtras().getInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT);
        contentId=getIntent().getExtras().getLong(ConstantUtil.INTENT_ID);
        String title=getIntent().getExtras().getString(ConstantUtil.INTENT_TITLE);
        tvTitle.setText(title);

        FragRoadComment fragRoadComment=FragRoadComment.getInstance(type, contentId,auth_state);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragRoadComment).show(fragRoadComment).commit();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @OnClick(R.id.ivLeft)
    public void onClick() {
        finish();
    }
}
