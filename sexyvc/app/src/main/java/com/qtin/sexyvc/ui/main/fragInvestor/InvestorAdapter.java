package com.qtin.sexyvc.ui.main.fragInvestor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.widget.DeviceUtils;
import com.qtin.sexyvc.ui.widget.TagContainer;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/6/9.
 */

public class InvestorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<InvestorEntity> data;

    public InvestorAdapter(Context context,ArrayList<InvestorEntity> data) {
        this.context=context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        InvestorEntity entity=data.get(position);
        ViewHolder holder=(ViewHolder)viewHolder;

        holder.ratingScore.setRating(3.2f);

        ArrayList<String> strings=new ArrayList<>();
        strings.add("种子轮 ~ D 轮及以后");
        strings.add("新三板前/后");
        strings.add("IPO 前");
        TagContainer tagContainer=new TagContainer(context);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tagContainer.setLayoutParams(params);
        holder.tagLinearLayout.removeAllViews();
        holder.tagLinearLayout.addView(tagContainer);
        tagContainer.setStringValue(strings, (int) (DeviceUtils.getScreenSize(context).x- com.jess.arms.utils.DeviceUtils.dpToPixel(context,20)));
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPosition)
        TextView tvPosition;
        @BindView(R.id.tvCommentNum)
        TextView tvCommentNum;
        @BindView(R.id.ratingScore)
        BaseRatingBar ratingScore;
        @BindView(R.id.tvScore)
        TextView tvScore;
        @BindView(R.id.tagLinearLayout)
        LinearLayout tagLinearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
