package com.qtin.sexyvc.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by ls on 17/7/13.
 */
public class InvestorInfoBean implements Parcelable {
    private long fund_id;
    private long investor_id;

    private String investor_name;
    private String investor_avatar;
    private long investor_uid;

    private String fund_name;
    private String title;
    private ArrayList<TagEntity> tags;

    public ArrayList<TagEntity> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagEntity> tags) {
        this.tags = tags;
    }

    public long getFund_id() {
        return fund_id;
    }

    public void setFund_id(long fund_id) {
        this.fund_id = fund_id;
    }

    public long getInvestor_id() {
        return investor_id;
    }

    public void setInvestor_id(long investor_id) {
        this.investor_id = investor_id;
    }

    public String getInvestor_name() {
        return investor_name;
    }

    public void setInvestor_name(String investor_name) {
        this.investor_name = investor_name;
    }

    public String getInvestor_avatar() {
        return investor_avatar;
    }

    public void setInvestor_avatar(String investor_avatar) {
        this.investor_avatar = investor_avatar;
    }

    public long getInvestor_uid() {
        return investor_uid;
    }

    public void setInvestor_uid(long investor_uid) {
        this.investor_uid = investor_uid;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.fund_id);
        dest.writeLong(this.investor_id);
        dest.writeString(this.investor_name);
        dest.writeString(this.investor_avatar);
        dest.writeLong(this.investor_uid);
        dest.writeString(this.fund_name);
        dest.writeString(this.title);
        dest.writeTypedList(this.tags);
    }

    public InvestorInfoBean() {
    }

    protected InvestorInfoBean(Parcel in) {
        this.fund_id = in.readLong();
        this.investor_id = in.readLong();
        this.investor_name = in.readString();
        this.investor_avatar = in.readString();
        this.investor_uid = in.readLong();
        this.fund_name = in.readString();
        this.title = in.readString();
        this.tags = in.createTypedArrayList(TagEntity.CREATOR);
    }

    public static final Parcelable.Creator<InvestorInfoBean> CREATOR = new Parcelable.Creator<InvestorInfoBean>() {
        @Override
        public InvestorInfoBean createFromParcel(Parcel source) {
            return new InvestorInfoBean(source);
        }

        @Override
        public InvestorInfoBean[] newArray(int size) {
            return new InvestorInfoBean[size];
        }
    };
}
