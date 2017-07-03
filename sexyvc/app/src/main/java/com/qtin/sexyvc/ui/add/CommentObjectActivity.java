package com.qtin.sexyvc.ui.add;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.add.frag.IndividualListFrag;
import com.qtin.sexyvc.ui.follow.search.ConcernSearchActivity;
import com.qtin.sexyvc.ui.user.message.MyViewPagerAdapter;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 17/6/29.
 */
public class CommentObjectActivity extends MyBaseActivity {


    @BindView(R.id.tvTab1)
    TextView tvTab1;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.tvTab2)
    TextView tvTab2;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private int type;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return true;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.search_tab_activity;
    }

    @Override
    protected void initData() {
        type=getIntent().getExtras().getInt(ConstantUtil.COMMENT_TYPE_INTENT);

        tvTab1.setSelected(true);
        line1.setSelected(true);

        ArrayList<Fragment> frags = new ArrayList<>();
        frags.add(IndividualListFrag.getInstance(ConstantUtil.DATA_FROM_LOCAL));
        frags.add(IndividualListFrag.getInstance(ConstantUtil.DATA_FROM_WEB));

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), frags);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tvTab1.setSelected(true);
                    line1.setVisibility(View.VISIBLE);
                    tvTab2.setSelected(false);
                    line2.setVisibility(View.INVISIBLE);
                } else {
                    tvTab1.setSelected(false);
                    line1.setVisibility(View.INVISIBLE);
                    tvTab2.setSelected(true);
                    line2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.ivLeft, R.id.searchContainer,R.id.tvTab1,R.id.tvTab2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.searchContainer:
                gotoActivity(ConcernSearchActivity.class);
                break;
            case R.id.tvTab1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tvTab2:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
