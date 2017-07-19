package com.qtin.sexyvc.ui.user.bean;

/**
 * Created by ls on 17/7/19.
 */

public class MsgBean {
    private long id;
    private long u_id;
    private String content;
    private int action_type;

    private String action_content;
    private long send_time;
    private long read_time;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAction_type() {
        return action_type;
    }

    public void setAction_type(int action_type) {
        this.action_type = action_type;
    }

    public String getAction_content() {
        return action_content;
    }

    public void setAction_content(String action_content) {
        this.action_content = action_content;
    }

    public long getSend_time() {
        return send_time;
    }

    public void setSend_time(long send_time) {
        this.send_time = send_time;
    }

    public long getRead_time() {
        return read_time;
    }

    public void setRead_time(long read_time) {
        this.read_time = read_time;
    }
}
/**

 "id": 2,
 "u_id": 0,
 "content": "这是一条测试通知",
 "action_type": "",

 "action_content": "",
 "send_time": 1499762967,
 "read_time": 0
 */