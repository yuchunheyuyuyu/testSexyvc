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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        FilterEntity entity = data.get(position);
        ViewHolder holder= (ViewHolder) viewHolder;
        if(position==data.size()-1){
            holder.viewLine.setVisibility(View.INVISIBLE);
        }else{
            holder.viewLine.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(StringUtil.formatString(entity.getName()));
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
