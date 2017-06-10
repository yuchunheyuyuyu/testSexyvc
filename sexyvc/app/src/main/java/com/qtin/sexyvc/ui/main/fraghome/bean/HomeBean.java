package com.qtin.sexyvc.ui.main.fraghome.bean;

/**
 * Created by ls on 17/6/10.
 */

public class HomeBean {

    private BannerBean banner;
    private NewsBean news;
    private InvestorBean inventors;
    private CommentBean comment;
    private SubjectBean subjects;

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
    }

    public NewsBean getNews() {
        return news;
    }

    public void setNews(NewsBean news) {
        this.news = news;
    }

    public InvestorBean getInventors() {
        return inventors;
    }

    public void setInventors(InvestorBean inventors) {
        this.inventors = inventors;
    }

    public CommentBean getComment() {
        return comment;
    }

    public void setComment(CommentBean comment) {
        this.comment = comment;
    }

    public SubjectBean getSubjects() {
        return subjects;
    }

    public void setSubjects(SubjectBean subjects) {
        this.subjects = subjects;
    }
}
