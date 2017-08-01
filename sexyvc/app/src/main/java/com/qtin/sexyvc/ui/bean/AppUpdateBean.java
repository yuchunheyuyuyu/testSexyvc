package com.qtin.sexyvc.ui.bean;

/**
 * Created by ls on 17/7/31.
 */

public class AppUpdateBean {
    private int version_code;
    private int constraint;
    private String apk_file_url;
    private String target_size;
    private String update_log;
    private String new_version;

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public int getConstraint() {
        return constraint;
    }

    public void setConstraint(int constraint) {
        this.constraint = constraint;
    }

    public String getApk_file_url() {
        return apk_file_url;
    }

    public void setApk_file_url(String apk_file_url) {
        this.apk_file_url = apk_file_url;
    }

    public String getTarget_size() {
        return target_size;
    }

    public void setTarget_size(String target_size) {
        this.target_size = target_size;
    }

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public String getNew_version() {
        return new_version;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }
}
/**
 "version_id": 1,
 "version_code": 11,
 "platform": 1,
 "constraint": 1,
 "apk_file_url": "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/apk/app-debug.apk",
 "target_size": "5M",
 "update_log": "1，添加删除信用卡接口\\r\\n2，添加vip认证\\r\\n3，区分自定义消费，一个小时不限制。\\r\\n4，添加放弃任务接口，小时内不生成。\\r\\n5，消费任务手动生成。",
 "new_version": "0.8.3",
 "create_time": 1501468712

 */