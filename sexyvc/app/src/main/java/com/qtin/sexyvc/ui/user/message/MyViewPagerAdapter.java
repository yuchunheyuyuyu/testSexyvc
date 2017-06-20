package com.qtin.sexyvc.ui.user.message;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * Created by ls on 17/6/20.
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> frags;

    public MyViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> frags) {
        super(fm);
        this.frags=frags;
    }

    @Override
    public Fragment getItem(int position) {
        return frags.get(position);
    }

    @Override
    public int getCount() {
        return frags==null?0:frags.size();
    }
}
