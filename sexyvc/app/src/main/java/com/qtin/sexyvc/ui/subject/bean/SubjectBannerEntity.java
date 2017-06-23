package com.qtin.sexyvc.ui.subject.bean;

import com.qtin.sexyvc.ui.bean.BannerEntity;
import com.qtin.sexyvc.ui.subject.SujectListAdapter;
import java.util.ArrayList;

/**
 * Created by ls on 17/6/23.
 */
public class SubjectBannerEntity implements SubjectListInterface{
    private ArrayList<BannerEntity> banners;

    public ArrayList<BannerEntity> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<BannerEntity> banners) {
        this.banners = banners;
    }

    @Override
    public int getType() {
        return SujectListAdapter.ITEM_BANNER;
    }
}
