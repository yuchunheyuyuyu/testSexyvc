package com.qtin.sexyvc.ui.road.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import java.util.ArrayList;

/**
 * Created by ls on 17/7/11.
 */
public class OptionFirstBean implements DataTypeInterface, Parcelable {
    private long option_id;
    private String option_content;
    private int add_question;
    private LinkQuestionBean link_question;
    private boolean isSelected;
    private ArrayList<AddQuestionBean> addQuestions;


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

    public int getAdd_question() {
        return add_question;
    }

    public void setAdd_question(int add_question) {
        this.add_question = add_question;
    }

    public LinkQuestionBean getLink_question() {
        return link_question;
    }

    public void setLink_question(LinkQuestionBean link_question) {
        this.link_question = link_question;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ArrayList<AddQuestionBean> getAddQuestions() {
        return addQuestions;
    }

    public void setAddQuestions(ArrayList<AddQuestionBean> addQuestions) {
        this.addQuestions = addQuestions;
    }

    @Override
    public int getType() {
        return DataTypeInterface.TYPE_OPTION;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.option_id);
        dest.writeString(this.option_content);
        dest.writeInt(this.add_question);
        dest.writeParcelable(this.link_question, flags);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.addQuestions);
    }

    public OptionFirstBean() {
    }

    protected OptionFirstBean(Parcel in) {
        this.option_id = in.readLong();
        this.option_content = in.readString();
        this.add_question = in.readInt();
        this.link_question = in.readParcelable(LinkQuestionBean.class.getClassLoader());
        this.isSelected = in.readByte() != 0;
        this.addQuestions = in.createTypedArrayList(AddQuestionBean.CREATOR);
    }

    public static final Creator<OptionFirstBean> CREATOR = new Creator<OptionFirstBean>() {
        @Override
        public OptionFirstBean createFromParcel(Parcel source) {
            return new OptionFirstBean(source);
        }

        @Override
        public OptionFirstBean[] newArray(int size) {
            return new OptionFirstBean[size];
        }
    };
}
