package com.qtin.sexyvc.ui.add;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.add.frag.IndividualListFrag;
import com.qtin.sexyvc.ui.add.search.SearchObjectActivity;
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

    private int typeComment;

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
        typeComment=getIntent().getExtras().getInt(ConstantUtil.COMMENT_TYPE_INTENT);

        StringUtil.setTextStyle(tvTab1,line1,true);

        ArrayList<Fragment> frags = new ArrayList<>();
        frags.add(IndividualListFrag.getInstance(ConstantUtil.DATA_FROM_LOCAL,typeComment));
        frags.add(IndividualListFrag.getInstance(ConstantUtil.DATA_FROM_WEB,typeComment));

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), frags);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    StringUtil.setTextStyle(tvTab1,line1,true);
                    StringUtil.setTextStyle(tvTab2,line2,false);
                } else {
                    StringUtil.setTextStyle(tvTab1,line1,false);
                    StringUtil.setTextStyle(tvTab2,line2,true);
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
                Bundle bundle=new Bundle();
                bundle.putInt(ConstantUtil.COMMENT_TYPE_INTENT,typeComment);
                gotoActivity(SearchObjectActivity.class,bundle);
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
