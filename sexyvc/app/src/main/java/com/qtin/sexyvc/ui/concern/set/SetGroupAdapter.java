package com.qtin.sexyvc.ui.concern.set;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.ConcernGroupEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/6/21.
 */
public class SetGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ConcernGroupEntity> data;
    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SetGroupAdapter(Context context, ArrayList<ConcernGroupEntity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_set_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder= (ViewHolder) viewHolder;
        if(position==data.size()){
            holder.ivPlus.setVisibility(View.VISIBLE);
            holder.tvMemberNum.setVisibility(View.INVISIBLE);
            holder.viewDot.setVisibility(View.INVISIBLE);
            holder.tvName.setVisibility(View.INVISIBLE);
            holder.tvAddGroup.setVisibility(View.VISIBLE);
        }else{
            holder.tvName.setVisibility(View.VISIBLE);
            holder.tvAddGroup.setVisibility(View.INVISIBLE);
            holder.ivPlus.setVisibility(View.INVISIBLE);
            holder.tvMemberNum.setVisibility(View.VISIBLE);
            ConcernGroupEntity entity=data.get(position);
            holder.tvName.setText(StringUtil.formatString(entity.getGroup_name()));
            holder.tvMemberNum.setText(""+entity.getMember_count());

            if(entity.isSelected()){
                holder.tvName.setSelected(true);
                holder.viewDot.setVisibility(View.VISIBLE);
            }else{
                holder.tvName.setSelected(false
                );
                holder.viewDot.setVisibility(View.INVISIBLE);
            }
        }

        holder.clickContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 1 : data.size() + 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.viewDot)
        View viewDot;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvMemberNum)
        TextView tvMemberNum;
        @BindView(R.id.ivPlus)
        ImageView ivPlus;
        @BindView(R.id.clickContainer)
        View clickContainer;
        @BindView(R.id.tvAddGroup)
        TextView tvAddGroup;

        ViewHolder(View view) {
            super(view);
            AutoUtils.auto(view);
            ButterKnife.bind(this, view);
        }
    }
}
