package com.qtin.sexyvc.ui.concern.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ConcernListEntity;
import com.qtin.sexyvc.ui.concern.detail.ConcernDetailActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/6/20.
 */
public class ConcernListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MyBaseActivity activity;
    private Context context;
    private ArrayList<ConcernListEntity> data;

    public ConcernListAdapter(Context context, ArrayList<ConcernListEntity> data) {
        this.context = context;
        this.data = data;
        activity = (MyBaseActivity) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false);
            return new ItemHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_local_search, parent, false);
            return new HistoryHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof ItemHolder){
            ItemHolder holder = (ItemHolder) viewHolder;
            if (position == data.size() - 1) {
                holder.wholeLine.setVisibility(View.VISIBLE);
                holder.marginLine.setVisibility(View.GONE);
            } else {
                holder.wholeLine.setVisibility(View.GONE);
                holder.marginLine.setVisibility(View.VISIBLE);
            }

            holder.clickContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.gotoActivity(ConcernDetailActivity.class);
                }
            });
        }else{
            HistoryHolder holder= (HistoryHolder) viewHolder;
            holder.tvClearHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPosition)
        TextView tvPosition;
        @BindView(R.id.marginLine)
        View marginLine;
        @BindView(R.id.wholeLine)
        View wholeLine;
        @BindView(R.id.clickContainer)
        View clickContainer;
        @BindView(R.id.ivAnthStatus)
        ImageView ivAnthStatus;

        ItemHolder(View view) {
            super(view);
            AutoUtils.auto(view);
            ButterKnife.bind(this, view);
        }
    }

    static class HistoryHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvClearHistory)
        TextView tvClearHistory;

        HistoryHolder(View view) {
            super(view);
            AutoUtils.auto(view);
            ButterKnife.bind(this, view);
        }
    }
}
