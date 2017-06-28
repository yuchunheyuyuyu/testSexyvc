package com.qtin.sexyvc.ui.bean;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

/**
 * Created by ls on 17/6/28.
 */

public class AdditionalBean implements DataTypeInterface {

    private long id;
    private String content;
    private long create_time;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_ADDITIONAL;
    }
}
