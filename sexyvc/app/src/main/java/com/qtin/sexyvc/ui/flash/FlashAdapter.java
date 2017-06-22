package com.qtin.sexyvc.ui.flash;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.flash.bean.FlashEntity;
import com.qtin.sexyvc.ui.widget.ExpandableTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/6/22.
 */
public class FlashAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int etvWidth;
    private ArrayList<FlashEntity> data;
    private SparseArray<Integer> mPositionsAndStates = new SparseArray<>();

    public FlashAdapter(ArrayList<FlashEntity> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flash, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ViewHolder holder= (ViewHolder) viewHolder;
        FlashEntity entity=data.get(position);

        if(etvWidth == 0){
            holder.tvContent.post(new Runnable() {
                @Override
                public void run() {
                    etvWidth = holder.tvContent.getWidth();
                }
            });
        }
        holder.tvContent.setTag(position);
        Integer state = mPositionsAndStates.get(position);
        ((ViewHolder) viewHolder).tvContent.updateForRecyclerView(entity.getContent(), etvWidth, state== null ? 0 : state);//第一次getview时肯定为etvWidth为0
        holder.tvContent.setExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(ExpandableTextView view) {
                Object obj = view.getTag();
                if(obj != null && obj instanceof Integer){
                    mPositionsAndStates.put((Integer)obj, view.getExpandState());
                }
            }

            @Override
            public void onShrink(ExpandableTextView view) {
                Object obj = view.getTag();
                if(obj != null && obj instanceof Integer){
                    mPositionsAndStates.put((Integer)obj, view.getExpandState());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvContent)
        ExpandableTextView tvContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
