package com.qtin.sexyvc.ui.subject.bean;

import com.qtin.sexyvc.ui.bean.TagEntity;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/27.
 */

public class SubjectContentEntity implements DataTypeInterface {
    private long subject_id;
    private String title;
    private String img_url;
    private String content;

    private String source;
    private long create_time;
    private ArrayList<TagEntity> tags;

    private int reply_count;
    private int praise_count;
    private int has_praise;

    public long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(long subject_id) {
        this.subject_id = subject_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public ArrayList<TagEntity> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagEntity> tags) {
        this.tags = tags;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getPraise_count() {
        return praise_count;
    }

    public void setPraise_count(int praise_count) {
        this.praise_count = praise_count;
    }

    public int getHas_praise() {
        return has_praise;
    }

    public void setHas_praise(int has_praise) {
        this.has_praise = has_praise;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_CONTENT;
    }
}
/**

 "subject_id": 1,
 "title": "你了解创新工场这家机构吗",
 "img_url": "image/2016/11/30/ff7b782e8aca1286d06debc8c39701aa.jpg",
 "content": "<p style=\"text-align: center;\">\t<img src=\"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&amp;quality=100&amp;size=b4000_4000&amp;sec=1495005852&amp;di=211e7fbbcf718b9ffa4213326aeac19b&amp;src=http://img3.duitang.com/uploads/item/201512/12/20151212213642_ZWa8G.jpeg\">\t<br>\t</p><p>\t</p><p style=\"text-align: center;\">\t\t各位小伙伴们，大家都听说过创新工场这家机构吗？在创投圈，创新工场应该是耳熟能详的一个名字了。创新工场是一家早期投资机构投资机构。</p>",
 "source": "",
 "create_time": 1488338947,
 "tags": [
 {
 "tag_id": 1,
 "tag_name": "创新工场"
 }
 ],
 "reply_count": 13,
 "praise_count": 2,
 "whether_praise": 0

 */