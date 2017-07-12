package com.qtin.sexyvc.ui.widget.optionview;

import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class OptionAdapter<T> {
    private List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangedListener;
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();

    public OptionAdapter(List<T> datas) {
        mTagDatas = datas;
    }

    public OptionAdapter(ArrayList<T> datas) {
        mTagDatas = datas;
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses) {
            set.add(pos);
        }
        setSelectedList(set);
    }

    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        if (set != null)
            mCheckedPosList.addAll(set);
        notifyDataChanged();
    }

    HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }


    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null)
            mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(MyFlowLayout parent, int position, T t);

    public boolean setSelected(int position, T t) {
        return false;
    }
}
