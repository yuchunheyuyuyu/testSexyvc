package com.qtin.sexyvc.ui.main.fraghome.bean;

import com.qtin.sexyvc.ui.bean.BannerEntity;
import com.qtin.sexyvc.ui.bean.HomeCommentBean;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.bean.SubjectEntity;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/10.
 */
public class HomeBean {

    private ArrayList<BannerEntity> banners;
    private ArrayList<NewsBean> flashes;
    private ArrayList<InvestorEntity> investors;
    private HomeCommentBean comments;
    private ArrayList<SubjectEntity> subjects;

    public ArrayList<BannerEntity> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<BannerEntity> banners) {
        this.banners = banners;
    }

    public ArrayList<NewsBean> getFlashes() {
        return flashes;
    }

    public void setFlashes(ArrayList<NewsBean> flashes) {
        this.flashes = flashes;
    }

    public ArrayList<InvestorEntity> getInvestors() {
        return investors;
    }

    public void setInvestors(ArrayList<InvestorEntity> investors) {
        this.investors = investors;
    }

    public HomeCommentBean getComments() {
        return comments;
    }

    public void setComments(HomeCommentBean comments) {
        this.comments = comments;
    }

    public ArrayList<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<SubjectEntity> subjects) {
        this.subjects = subjects;
    }
}
