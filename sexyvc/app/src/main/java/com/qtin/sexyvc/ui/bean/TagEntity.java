package com.qtin.sexyvc.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ls on 17/6/22.
 */

public class TagEntity implements Parcelable {
    private int tag_count;
    private long tag_id;
    private String tag_name;
    private boolean isSelected;
    private boolean isCustom;

    public int getTag_count() {
        return tag_count;
    }

    public void setTag_count(int tag_count) {
        this.tag_count = tag_count;
    }

    public long getTag_id() {
        return tag_id;
    }

    public void setTag_id(long tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tag_count);
        dest.writeLong(this.tag_id);
        dest.writeString(this.tag_name);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isCustom ? (byte) 1 : (byte) 0);
    }

    public TagEntity() {
    }

    protected TagEntity(Parcel in) {
        this.tag_count = in.readInt();
        this.tag_id = in.readLong();
        this.tag_name = in.readString();
        this.isSelected = in.readByte() != 0;
        this.isCustom = in.readByte() != 0;
    }

    public static final Creator<TagEntity> CREATOR = new Creator<TagEntity>() {
        @Override
        public TagEntity createFromParcel(Parcel source) {
            return new TagEntity(source);
        }

        @Override
        public TagEntity[] newArray(int size) {
            return new TagEntity[size];
        }
    };
}
