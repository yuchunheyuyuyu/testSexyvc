package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class EfficiencyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FilterEntity> data;

    public EfficiencyAdapter(Context context, ArrayList<FilterEntity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FilterEntity entity = data.get(position);
        ViewHolder holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_filter_listview, parent, false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if(position==data.size()-1){
            holder.viewLine.setVisibility(View.INVISIBLE);
        }else{
            holder.viewLine.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(StringUtil.formatString(entity.getName()));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.viewLine)
        View viewLine;

        ViewHolder(View view) {
            AutoUtils.autoSize(view);//适配
            ButterKnife.bind(this, view);
        }
    }
}
