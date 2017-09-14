package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/9/5.
 */

public class InfluencyBean {
    private int comment_number;
    private int roadshow_number;
    private int comment_praised_number;

    private int has_project;
    private int u_auth_state;
    private int u_auth_type;
    private int auth_passed;

    public int getHas_project() {
        return has_project;
    }

    public void setHas_project(int has_project) {
        this.has_project = has_project;
    }

    public int getU_auth_state() {
        return u_auth_state;
    }

    public void setU_auth_state(int u_auth_state) {
        this.u_auth_state = u_auth_state;
    }

    public int getU_auth_type() {
        return u_auth_type;
    }

    public void setU_auth_type(int u_auth_type) {
        this.u_auth_type = u_auth_type;
    }

    public int getAuth_passed() {
        return auth_passed;
    }

    public void setAuth_passed(int auth_passed) {
        this.auth_passed = auth_passed;
    }

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }

    public int getRoadshow_number() {
        return roadshow_number;
    }

    public void setRoadshow_number(int roadshow_number) {
        this.roadshow_number = roadshow_number;
    }

    public int getComment_praised_number() {
        return comment_praised_number;
    }

    public void setComment_praised_number(int comment_praised_number) {
        this.comment_praised_number = comment_praised_number;
    }
}
