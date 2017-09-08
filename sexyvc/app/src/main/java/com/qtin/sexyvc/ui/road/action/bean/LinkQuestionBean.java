package com.qtin.sexyvc.ui.road.action.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ls on 17/7/11.
 */

public class LinkQuestionBean implements Parcelable {

    private long question_id;
    private String title;
    private int add_option;
    private int multi_select;
    private ArrayList<OptionSecondBean> options;

    public long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(long question_id) {
        this.question_id = question_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAdd_option() {
        return add_option;
    }

    public void setAdd_option(int add_option) {
        this.add_option = add_option;
    }

    public int getMulti_select() {
        return multi_select;
    }

    public void setMulti_select(int multi_select) {
        this.multi_select = multi_select;
    }

    public ArrayList<OptionSecondBean> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<OptionSecondBean> options) {
        this.options = options;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.question_id);
        dest.writeString(this.title);
        dest.writeInt(this.add_option);
        dest.writeInt(this.multi_select);
        dest.writeTypedList(this.options);
    }

    public LinkQuestionBean() {
    }

    protected LinkQuestionBean(Parcel in) {
        this.question_id = in.readLong();
        this.title = in.readString();
        this.add_option = in.readInt();
        this.multi_select = in.readInt();
        this.options = in.createTypedArrayList(OptionSecondBean.CREATOR);
    }

    public static final Parcelable.Creator<LinkQuestionBean> CREATOR = new Parcelable.Creator<LinkQuestionBean>() {
        @Override
        public LinkQuestionBean createFromParcel(Parcel source) {
            return new LinkQuestionBean(source);
        }

        @Override
        public LinkQuestionBean[] newArray(int size) {
            return new LinkQuestionBean[size];
        }
    };
}
