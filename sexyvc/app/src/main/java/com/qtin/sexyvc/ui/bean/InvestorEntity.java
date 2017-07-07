package com.qtin.sexyvc.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;

import java.util.ArrayList;

/**
 * Created by ls on 17/6/9.
 */

public class InvestorEntity implements DataTypeInterface, Parcelable {

    private long investor_id;
    private String investor_name;
    private String investor_avatar;
    private String fund_name;
    private String title;
    private float score;
    private long u_id;
    private long comment_number;
    private long fund_id;
    private String investor_title;

    public String getInvestor_title() {
        return investor_title;
    }

    public void setInvestor_title(String investor_title) {
        this.investor_title = investor_title;
    }

    private ArrayList<TagEntity> tags;

    public long getComment_number() {
        return comment_number;
    }

    public void setComment_number(long comment_number) {
        this.comment_number = comment_number;
    }

    public long getFund_id() {
        return fund_id;
    }

    public void setFund_id(long fund_id) {
        this.fund_id = fund_id;
    }

    public ArrayList<TagEntity> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagEntity> tags) {
        this.tags = tags;
    }

    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
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

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_INVESTOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.investor_id);
        dest.writeString(this.investor_name);
        dest.writeString(this.investor_avatar);
        dest.writeString(this.fund_name);
        dest.writeString(this.title);
        dest.writeFloat(this.score);
        dest.writeLong(this.u_id);
        dest.writeLong(this.comment_number);
        dest.writeLong(this.fund_id);
        dest.writeString(this.investor_title);
        dest.writeList(this.tags);
    }

    public InvestorEntity() {
    }

    protected InvestorEntity(Parcel in) {
        this.investor_id = in.readLong();
        this.investor_name = in.readString();
        this.investor_avatar = in.readString();
        this.fund_name = in.readString();
        this.title = in.readString();
        this.score = in.readFloat();
        this.u_id = in.readLong();
        this.comment_number = in.readLong();
        this.fund_id = in.readLong();
        this.investor_title = in.readString();
        this.tags = new ArrayList<TagEntity>();
        in.readList(this.tags, TagEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<InvestorEntity> CREATOR = new Parcelable.Creator<InvestorEntity>() {
        @Override
        public InvestorEntity createFromParcel(Parcel source) {
            return new InvestorEntity(source);
        }

        @Override
        public InvestorEntity[] newArray(int size) {
            return new InvestorEntity[size];
        }
    };
}
