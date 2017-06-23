package com.qtin.sexyvc.ui.subject.bean;

import com.qtin.sexyvc.ui.subject.SujectListAdapter;

/**
 * Created by ls on 17/6/23.
 */
public class SubjectListEntity implements SubjectListInterface {

    private long subject_id;
    private String title;
    private String img_url;
    private String source;
    private long create_time;

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

    @Override
    public int getType() {
        return SujectListAdapter.ITEM_NORMAL;
    }
}
/**
 *  "subject_id": 14,
 "title": "贾跃亭去干吗了",
 "img_url": "201706151618115z1EtaSJ.jpeg",
 "source": "考虑你好高回报",
 "create_time": 1497514715
 */