package com.qtin.sexyvc.ui.user.bean;

/**
 * Created by ls on 17/7/19.
 */

public class MsgBean {
    private long id;
    private long u_id;
    private String title;
    private String content;


    private int message_type;
    private long create_time;
    private long read_time;

    private String action_type;
    private String action_content;
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getRead_time() {
        return read_time;
    }

    public void setRead_time(long read_time) {
        this.read_time = read_time;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getAction_content() {
        return action_content;
    }

    public void setAction_content(String action_content) {
        this.action_content = action_content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}