package com.qtin.sexyvc.ui.road;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.road.bean.OptionSecondBean;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/7/12.
 */
public class OptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<OptionSecondBean> data;
    private int multi_select;

    public OptionAdapter(Context context, ArrayList<OptionSecondBean> data,int multi_select) {
        this.context = context;
        this.data = data;
        this.multi_select=multi_select;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_option_textview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ViewHolder holder= (ViewHolder) viewHolder;
        final OptionSecondBean bean=data.get(position);
        holder.tvContent.setText(StringUtil.formatString(bean.getOption_content()));
        if(bean.isSelected()){
            holder.tvContent.setSelected(true);
        }else{
            holder.tvContent.setSelected(false);
        }
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单选还是多选
                if(multi_select==0){
                    for(OptionSecondBean secondBean:data){
                        secondBean.setSelected(false);
                    }
                    bean.setSelected(true);
                    holder.tvContent.setSelected(true);
                    notifyDataSetChanged();
                }else{
                    if(bean.isSelected()){
                        bean.setSelected(false);
                        holder.tvContent.setSelected(false);
                    }else{
                        bean.setSelected(true);
                        holder.tvContent.setSelected(true);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvContent)
        TextView tvContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
