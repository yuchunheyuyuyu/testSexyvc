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
    private int score_value;
    private int has_score;
    private int has_comment;
    private int has_roadshow;
    private String comment_title;
    private long comment_id;

    private int intent;//0代表进入路演评价，1代表进入文字评价

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

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<TagEntity> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagEntity> tags) {
        this.tags = tags;
    }

    public int getScore_value() {
        return score_value;
    }

    public void setScore_value(int score_value) {
        this.score_value = score_value;
    }

    public int getHas_score() {
        return has_score;
    }

    public void setHas_score(int has_score) {
        this.has_score = has_score;
    }

    public int getHas_comment() {
        return has_comment;
    }

    public void setHas_comment(int has_comment) {
        this.has_comment = has_comment;
    }

    public int getHas_roadshow() {
        return has_roadshow;
    }

    public void setHas_roadshow(int has_roadshow) {
        this.has_roadshow = has_roadshow;
    }

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

    public int getIntent() {
        return intent;
    }

    public void setIntent(int intent) {
        this.intent = intent;
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
        dest.writeInt(this.score_value);
        dest.writeInt(this.has_score);
        dest.writeInt(this.has_comment);
        dest.writeInt(this.has_roadshow);
        dest.writeString(this.comment_title);
        dest.writeLong(this.comment_id);
        dest.writeInt(this.intent);
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
        this.score_value = in.readInt();
        this.has_score = in.readInt();
        this.has_comment = in.readInt();
        this.has_roadshow = in.readInt();
        this.comment_title = in.readString();
        this.comment_id = in.readLong();
        this.intent = in.readInt();
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
