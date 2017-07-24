package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.FilterEntity;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/6/9.
 */
public class EfficiencyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<FilterEntity> data;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public EfficiencyAdapter(Context context, ArrayList<FilterEntity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_filter_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final FilterEntity entity = data.get(position);
        ViewHolder holder= (ViewHolder) viewHolder;

        if(entity.isSelected()){
            holder.tvName.setTextColor(context.getResources().getColor(R.color.barbie_pink_two));
            holder.viewLine.setBackgroundColor(context.getResources().getColor(R.color.barbie_pink_two));
        }else{
            holder.tvName.setTextColor(context.getResources().getColor(R.color.black50));
            holder.viewLine.setBackgroundColor(context.getResources().getColor(R.color.silver_two));
        }

        if(position==data.size()-1){
            holder.viewLine.setVisibility(View.INVISIBLE);
        }else{
            holder.viewLine.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(StringUtil.formatString(entity.getType_name()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onClickItem(position);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.viewLine)
        View viewLine;

        ViewHolder(View view) {
            super(view);
            AutoUtils.autoSize(view);//适配
            ButterKnife.bind(this, view);
        }
    }
}
