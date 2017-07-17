package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/7/17.
 */

public class CommentEvent {
    private String type;
    private int score;
    private String comment_title;
    private long comment_id;

    public String getComment_title() {
        return comment_title;
    }

    public void setComment_title(String comment_title) {
        this.comment_title = comment_title;
    }

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public CommentEvent() {
    }

    public CommentEvent(String type, int score) {
        this.type = type;
        this.score = score;
    }

    public CommentEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
