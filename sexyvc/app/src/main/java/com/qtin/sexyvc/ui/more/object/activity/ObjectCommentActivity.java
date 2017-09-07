package com.qtin.sexyvc.ui.more.object.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.more.object.activity.di.DaggerObjectCommentComponent;
import com.qtin.sexyvc.ui.more.object.activity.di.ObjectCommentModule;
import com.qtin.sexyvc.ui.more.object.road.FragRoadComment;
import com.qtin.sexyvc.ui.user.message.MyViewPagerAdapter;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/4/26.
 */
public class ObjectCommentActivity extends MyBaseActivity<ObjectCommentPresent> implements ObjectCommentContract.View {


    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivRight)
    ImageView ivRight;
    @BindView(R.id.tvTextComment)
    TextView tvTextComment;
    @BindView(R.id.lineText)
    View lineText;
    @BindView(R.id.tvRoadComment)
    TextView tvRoadComment;
    @BindView(R.id.lineRoad)
    View lineRoad;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.viewLine)
    View viewLine;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerObjectCommentComponent.builder().appComponent(appComponent).objectCommentModule(new ObjectCommentModule(this)).build().inject(this);
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.object_comment_activity;
    }

    @Override
    protected void initData() {
        int type = getIntent().getExtras().getInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT);
        long contentId = getIntent().getExtras().getLong(ConstantUtil.INTENT_ID);

        String title = getIntent().getExtras().getString(ConstantUtil.INTENT_TITLE);
        tvTitle.setText(title);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.icon_nav_comment);
        viewLine.setVisibility(View.GONE);

        ArrayList<Fragment> frags = new ArrayList<>();
        if(type==ConstantUtil.TYPE_INVESTOR){
            frags.add(FragRoadComment.getInstance(ConstantUtil.TYPE_INVESTOR, contentId,false));
            frags.add(FragRoadComment.getInstance(ConstantUtil.TYPE_INVESTOR_ROAD, contentId,true));
        }else{
            frags.add(FragRoadComment.getInstance(ConstantUtil.TYPE_FUND, contentId,false));
            frags.add(FragRoadComment.getInstance(ConstantUtil.TYPE_FUND_ROAD, contentId,true));
        }

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), frags);
        viewPager.setAdapter(adapter);
        setSelectStatus(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setSelectStatus(int currentItem) {
        if (currentItem == 0) {
            tvTextComment.setSelected(true);
            lineText.setVisibility(View.VISIBLE);
            tvRoadComment.setSelected(false);
            lineRoad.setVisibility(View.GONE);
        } else {
            tvTextComment.setSelected(false);
            lineText.setVisibility(View.GONE);
            tvRoadComment.setSelected(true);
            lineRoad.setVisibility(View.VISIBLE);
        }
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

    @OnClick({R.id.ivLeft, R.id.ivRight, R.id.textContainer, R.id.roadContainer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.ivRight:
                break;
            case R.id.textContainer:
                viewPager.setCurrentItem(0);
                break;
            case R.id.roadContainer:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
