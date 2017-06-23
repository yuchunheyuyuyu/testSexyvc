package com.qtin.sexyvc.ui.subject.bean;

import com.qtin.sexyvc.ui.bean.BannerEntity;
import java.util.ArrayList;

/**
 * Created by ls on 17/6/23.
 */

public class SubjectBean {
    private ArrayList<BannerEntity> banners;
    private Subject subjects;

    public ArrayList<BannerEntity> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<BannerEntity> banners) {
        this.banners = banners;
    }

    public Subject getSubjects() {
        return subjects;
    }

    public void setSubjects(Subject subjects) {
        this.subjects = subjects;
    }
}
