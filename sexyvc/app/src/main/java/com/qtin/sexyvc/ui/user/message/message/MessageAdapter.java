package com.qtin.sexyvc.ui.user.message.message;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.user.bean.MsgBean;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/7/19.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MsgBean> data;
    private OnItemClickListener onItemClickListener;
    private boolean isHasLoadMore;

    public void setHasLoadMore(boolean hasLoadMore) {
        isHasLoadMore = hasLoadMore;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MessageAdapter(ArrayList<MsgBean> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < data.size()) {
            return ConstantUtil.TYPE_NORMAL;
        }
        return ConstantUtil.TYPE_FOOTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == ConstantUtil.TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_has_load_all, parent, false);
            return new FootHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_has_load_all, parent, false);
            return new FootHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return isHasLoadMore ? data.size() + 1 : data.size();
        }
    }

    static class FootHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvHasLoadAll)
        TextView tvHasLoadAll;

        FootHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
