package com.qtin.sexyvc.ui.road.action.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ls on 17/7/11.
 */

public class OptionSecondBean implements Parcelable {
    private long option_id;
    private String option_content;
    private boolean selected;

    public long getOption_id() {
        return option_id;
    }

    public void setOption_id(long option_id) {
        this.option_id = option_id;
    }

    public String getOption_content() {
        return option_content;
    }

    public void setOption_content(String option_content) {
        this.option_content = option_content;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.option_id);
        dest.writeString(this.option_content);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    public OptionSecondBean() {
    }

    protected OptionSecondBean(Parcel in) {
        this.option_id = in.readLong();
        this.option_content = in.readString();
        this.selected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<OptionSecondBean> CREATOR = new Parcelable.Creator<OptionSecondBean>() {
        @Override
        public OptionSecondBean createFromParcel(Parcel source) {
            return new OptionSecondBean(source);
        }

        @Override
        public OptionSecondBean[] newArray(int size) {
            return new OptionSecondBean[size];
        }
    };
}
