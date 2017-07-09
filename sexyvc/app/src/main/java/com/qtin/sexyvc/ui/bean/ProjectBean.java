package com.qtin.sexyvc.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ls on 17/7/9.
 */
public class ProjectBean implements Parcelable {
    private String token;
    private String logo;
    private String project_name;
    private String short_intro;
    private long domain_id;
    private long last_stage_id;
    private long last_financial_amount;
    private int last_currency;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getShort_intro() {
        return short_intro;
    }

    public void setShort_intro(String short_intro) {
        this.short_intro = short_intro;
    }

    public long getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(long domain_id) {
        this.domain_id = domain_id;
    }

    public long getLast_stage_id() {
        return last_stage_id;
    }

    public void setLast_stage_id(long last_stage_id) {
        this.last_stage_id = last_stage_id;
    }

    public long getLast_financial_amount() {
        return last_financial_amount;
    }

    public void setLast_financial_amount(long last_financial_amount) {
        this.last_financial_amount = last_financial_amount;
    }

    public int getLast_currency() {
        return last_currency;
    }

    public void setLast_currency(int last_currency) {
        this.last_currency = last_currency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.logo);
        dest.writeString(this.project_name);
        dest.writeString(this.short_intro);
        dest.writeLong(this.domain_id);
        dest.writeLong(this.last_stage_id);
        dest.writeLong(this.last_financial_amount);
        dest.writeInt(this.last_currency);
    }

    public ProjectBean() {
    }

    protected ProjectBean(Parcel in) {
        this.token = in.readString();
        this.logo = in.readString();
        this.project_name = in.readString();
        this.short_intro = in.readString();
        this.domain_id = in.readLong();
        this.last_stage_id = in.readLong();
        this.last_financial_amount = in.readLong();
        this.last_currency = in.readInt();
    }

    public static final Parcelable.Creator<ProjectBean> CREATOR = new Parcelable.Creator<ProjectBean>() {
        @Override
        public ProjectBean createFromParcel(Parcel source) {
            return new ProjectBean(source);
        }

        @Override
        public ProjectBean[] newArray(int size) {
            return new ProjectBean[size];
        }
    };
}
