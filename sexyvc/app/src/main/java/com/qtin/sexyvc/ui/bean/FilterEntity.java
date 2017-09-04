package com.qtin.sexyvc.ui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ls on 17/6/9.
 * 筛选条件
 */
@Entity
public class FilterEntity implements Parcelable {
    private int key_id;
    private String type_name;
    @Id
    private long type_id;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    public int getKey_id() {
        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.key_id);
        dest.writeString(this.type_name);
        dest.writeLong(this.type_id);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public FilterEntity() {
    }

    protected FilterEntity(Parcel in) {
        this.key_id = in.readInt();
        this.type_name = in.readString();
        this.type_id = in.readLong();
        this.isSelected = in.readByte() != 0;
    }

    @Generated(hash = 307333355)
    public FilterEntity(int key_id, String type_name, long type_id, boolean isSelected) {
        this.key_id = key_id;
        this.type_name = type_name;
        this.type_id = type_id;
        this.isSelected = isSelected;
    }

    public static final Parcelable.Creator<FilterEntity> CREATOR = new Parcelable.Creator<FilterEntity>() {
        @Override
        public FilterEntity createFromParcel(Parcel source) {
            return new FilterEntity(source);
        }

        @Override
        public FilterEntity[] newArray(int size) {
            return new FilterEntity[size];
        }
    };
}
