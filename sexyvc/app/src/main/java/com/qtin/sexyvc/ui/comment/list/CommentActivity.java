package com.qtin.sexyvc.ui.comment.list;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.comment.list.frag.CommentLastFrag;
import com.qtin.sexyvc.ui.user.message.MyViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/6/16.
 */
public class CommentActivity extends MyBaseActivity {

    @BindView(R.id.tvComentNew)
    TextView tvComentNew;
    @BindView(R.id.lineNew)
    View lineNew;
    @BindView(R.id.tvCommentSelected)
    TextView tvCommentSelected;
    @BindView(R.id.lineSelected)
    View lineSelected;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return true;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.comment_list_activity;
    }

    @Override
    protected void initData() {
        tvComentNew.setSelected(true);
        lineNew.setSelected(true);

        ArrayList<Fragment> frags=new ArrayList<>();
        frags.add(CommentLastFrag.getInstance(0));
        frags.add(CommentLastFrag.getInstance(1));
        MyViewPagerAdapter adapter=new MyViewPagerAdapter(getSupportFragmentManager(),frags);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    tvComentNew.setSelected(true);
                    lineNew.setVisibility(View.VISIBLE);
                    tvCommentSelected.setSelected(false);
                    lineSelected.setVisibility(View.INVISIBLE);
                }else{
                    tvComentNew.setSelected(false);
                    lineNew.setVisibility(View.INVISIBLE);
                    tvCommentSelected.setSelected(true);
                    lineSelected.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.ivLeft, R.id.tvComentNew, R.id.tvCommentSelected})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.tvComentNew:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tvCommentSelected:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
