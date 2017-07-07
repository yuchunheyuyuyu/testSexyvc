package com.qtin.sexyvc.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ls on 17/7/3.
 */

public class CaseBean implements Parcelable {
    private long case_id;
    private String case_name;
    private String case_logo;

    public long getCase_id() {
        return case_id;
    }

    public void setCase_id(long case_id) {
        this.case_id = case_id;
    }

    public String getCase_name() {
        return case_name;
    }

    public void setCase_name(String case_name) {
        this.case_name = case_name;
    }

    public String getCase_logo() {
        return case_logo;
    }

    public void setCase_logo(String case_logo) {
        this.case_logo = case_logo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.case_id);
        dest.writeString(this.case_name);
        dest.writeString(this.case_logo);
    }

    public CaseBean() {
    }

    protected CaseBean(Parcel in) {
        this.case_id = in.readLong();
        this.case_name = in.readString();
        this.case_logo = in.readString();
    }

    public static final Parcelable.Creator<CaseBean> CREATOR = new Parcelable.Creator<CaseBean>() {
        @Override
        public CaseBean createFromParcel(Parcel source) {
            return new CaseBean(source);
        }

        @Override
        public CaseBean[] newArray(int size) {
            return new CaseBean[size];
        }
    };
}
