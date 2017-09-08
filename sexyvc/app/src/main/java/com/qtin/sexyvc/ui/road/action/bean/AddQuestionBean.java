package com.qtin.sexyvc.ui.road.action.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ls on 17/7/12.
 */

public class AddQuestionBean implements Parcelable {
    private String title;
    private String answer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.answer);
    }

    public AddQuestionBean() {
    }

    protected AddQuestionBean(Parcel in) {
        this.title = in.readString();
        this.answer = in.readString();
    }

    public static final Parcelable.Creator<AddQuestionBean> CREATOR = new Parcelable.Creator<AddQuestionBean>() {
        @Override
        public AddQuestionBean createFromParcel(Parcel source) {
            return new AddQuestionBean(source);
        }

        @Override
        public AddQuestionBean[] newArray(int size) {
            return new AddQuestionBean[size];
        }
    };
}
