package com.qtin.sexyvc.ui.user.message;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.user.message.message.MessageFrag;
import com.qtin.sexyvc.ui.user.message.notice.NoticeFrag;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/6/16.
 */
public class MessageActivity extends MyBaseActivity {


    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.lineMessage)
    View lineMessage;
    @BindView(R.id.tvNotice)
    TextView tvNotice;
    @BindView(R.id.lineNotice)
    View lineNotice;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private MessageFrag messageFrag;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return true;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.message_activity;
    }

    @Override
    protected void initData() {

        StringUtil.setTextStyle(tvMessage,lineMessage,true);

        ArrayList<Fragment> frags=new ArrayList<>();
        messageFrag=new MessageFrag();
        frags.add(messageFrag);
        frags.add(new NoticeFrag());
        MyViewPagerAdapter adapter=new MyViewPagerAdapter(getSupportFragmentManager(),frags);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    StringUtil.setTextStyle(tvMessage,lineMessage,true);
                    StringUtil.setTextStyle(tvNotice,lineNotice,false);
                    tvRight.setVisibility(View.VISIBLE);
                }else{
                    StringUtil.setTextStyle(tvMessage,lineMessage,false);
                    StringUtil.setTextStyle(tvNotice,lineNotice,true);
                    tvRight.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setTvRightSelected(boolean isSelected){
        tvRight.setSelected(isSelected);
    }

    @OnClick({R.id.ivLeft, R.id.tvRight,R.id.tvMessage,R.id.tvNotice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvRight:
                if(tvRight.isSelected()){
                    tvRight.setSelected(false);
                    messageFrag.changeAllReadStatus();
                }

                break;
            case R.id.tvMessage:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tvNotice:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
