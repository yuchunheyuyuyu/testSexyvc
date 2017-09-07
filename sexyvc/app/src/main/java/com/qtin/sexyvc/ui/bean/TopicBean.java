package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/9/6.
 */

public class TopicBean {
    private long topic_id;
    private String title;
    private String cover_pic;
    private String summary;
    private String intro;
    private int status;
    private long create_time;

    public long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(long topic_id) {
        this.topic_id = topic_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
}
/**
 *  "topic_id": 1,
 "title": "热评标题修改",
 "cover_pic": "image/2017/07/27/b9184a2b4bbc1942b25f75b60e4ad91f.png",
 "summary": "",
 "intro": "",
 "status": 0,
 "create_time": 1504082377
 *
 */