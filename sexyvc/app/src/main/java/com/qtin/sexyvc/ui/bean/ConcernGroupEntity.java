package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/6/20.
 */

public class ConcernGroupEntity {

    private long group_id;
    private String group_name;
    private long u_id;
    private int status;
    private int member_count;
    private long create_time;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public void changeStatus(){
        if(isSelected){
            isSelected=false;
        }else{
            isSelected=true;
        }
    }
}
/**


 "group_id": 1,
 "group_name": "医疗行业投资人",
 "u_id": 2,
 "status": 1,
 "member_count": 0,
 "create_time": 1497510458
 */