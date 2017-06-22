package com.qtin.sexyvc.ui.main.fragconcern.di;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.ConcernGroupEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.bean.OnLongItemClickListener;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/6/20.
 */
public class ConcernGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ConcernGroupEntity> data;
    private MyBaseActivity activity;
    private OnLongItemClickListener longItemClickListener;
    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setLongItemClickListener(OnLongItemClickListener longItemClickListener) {
        this.longItemClickListener = longItemClickListener;
    }

    public ConcernGroupAdapter(Context context, ArrayList<ConcernGroupEntity> data) {
        this.context = context;
        this.data = data;
        activity= (MyBaseActivity) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.concern_group_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder= (ViewHolder) viewHolder;
        ConcernGroupEntity entity=data.get(position);
        if(position==data.size()-1){
            holder.marginLine.setVisibility(View.GONE);
            holder.wholeLine.setVisibility(View.VISIBLE);
        }else{
            holder.marginLine.setVisibility(View.VISIBLE);
            holder.wholeLine.setVisibility(View.GONE);
        }
        holder.tvName.setText(StringUtil.formatString(entity.getGroup_name()));
        holder.tvNumber.setText(""+entity.getMember_count());
        holder.container.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                itemClickListener.onClickItem(position);
            }
        });
        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(longItemClickListener!=null){
                    longItemClickListener.onLongClickItem(position);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.marginLine)
        View marginLine;
        @BindView(R.id.wholeLine)
        View wholeLine;
        @BindView(R.id.container)
        View container;

        ViewHolder(View view) {
            super(view);
            AutoUtils.auto(view);
            ButterKnife.bind(this, view);
        }
    }
}
