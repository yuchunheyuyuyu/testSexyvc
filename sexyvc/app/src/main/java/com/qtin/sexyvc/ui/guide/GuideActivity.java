package com.qtin.sexyvc.ui.guide;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.login.account.create.CreateActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by ls on 17/7/28.
 */
public class GuideActivity extends MyBaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<View> views=new ArrayList<>();

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.guide_activity;
    }

    @Override
    protected void initData() {

        SharedPreferences preferences=getSharedPreferences("guide", Context.MODE_PRIVATE);
        boolean hasShowGuide=preferences.getBoolean("has_guide_value",false);

        if(hasShowGuide){
            gotoActivity(CreateActivity.class);
        } else {
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("has_guide_value",true);
            editor.commit();

            for (int i = 0; i < 4; i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_guide, null);
                ImageView ivGuide = (ImageView) view.findViewById(R.id.ivGuide);
                TextView tvGotoApp = (TextView) view.findViewById(R.id.tvGotoApp);

                if (i == 0) {
                    tvGotoApp.setVisibility(View.GONE);
                    ivGuide.setImageResource(R.drawable.guide_page1);
                } else if (i == 1) {
                    tvGotoApp.setVisibility(View.GONE);
                    ivGuide.setImageResource(R.drawable.guide_page2);
                } else if (i == 2) {
                    tvGotoApp.setVisibility(View.GONE);
                    ivGuide.setImageResource(R.drawable.guide_page3);
                } else if (i == 3) {
                    tvGotoApp.setVisibility(View.VISIBLE);
                    tvGotoApp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gotoActivity(CreateActivity.class);
                        }
                    });
                    ivGuide.setImageResource(R.drawable.guide_page4);
                }
                views.add(view);
            }
            viewPager.setAdapter(pagerAdapter);
        }

    }

    PagerAdapter pagerAdapter=new PagerAdapter() {
        @Override
        public int getCount() {
            return views==null?0:views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }
    };
}
