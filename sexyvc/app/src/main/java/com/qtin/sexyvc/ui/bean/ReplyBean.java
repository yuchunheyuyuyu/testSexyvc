package com.qtin.sexyvc.ui.bean;

import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

/**
 * Created by ls on 17/6/27.
 */
public class ReplyBean implements DataTypeInterface {
    private long reply_id;
    private String reply_content;
    private long parent_id;
    private String u_avatar;

    private String u_nickname;
    private int like;
    private int has_praise;
    private long create_time;

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getReply_id() {
        return reply_id;
    }

    public void setReply_id(long reply_id) {
        this.reply_id = reply_id;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public String getU_avatar() {
        return u_avatar;
    }

    public void setU_avatar(String u_avatar) {
        this.u_avatar = u_avatar;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getHas_praise() {
        return has_praise;
    }

    public void setHas_praise(int has_praise) {
        this.has_praise = has_praise;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_COMMENT;
    }
}
/**

 "reply_id": 147,
 "reply_content": "哈哈哈哈哈哈哈哈",
 "parent_id": 0,
 "u_avatar": null,
 "u_nickname": null,
 "like": 0,
 "whether_praise": 0

 */