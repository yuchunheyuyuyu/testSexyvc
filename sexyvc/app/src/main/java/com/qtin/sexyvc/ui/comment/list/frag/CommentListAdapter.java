package com.qtin.sexyvc.ui.comment.list.frag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.utils.DateUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/7/19.
 */

public class CommentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CommentBean> data;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CommentListAdapter(ArrayList<CommentBean> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view  );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder= (ViewHolder) viewHolder;
        CommentBean entity=data.get(position);

        if(StringUtil.isBlank(entity.getDomain_name())){
            holder.tvCommentTag.setVisibility(View.GONE);
        }else{
            holder.tvCommentTag.setVisibility(View.VISIBLE);
            holder.tvCommentTag.setText(entity.getDomain_name());
        }
        holder.tvTime.setText(DateUtil.getSpecialDate(entity.getCreate_time()));
        holder.tvFrom.setText(StringUtil.formatString(entity.getU_nickname())+" 评论了");
        holder.tvTo.setText(entity.getInvestor_name()+"@"+entity.getFund_name());
        holder.ratingScore.invalidate();
        holder.ratingScore.setRating10(entity.getScore());
        holder.tvComentContent.setText(StringUtil.formatString(entity.getTitle()));
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
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvCommentTag)
        TextView tvCommentTag;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.tvTo)
        TextView tvTo;
        @BindView(R.id.ratingScore)
        BaseRatingBar ratingScore;
        @BindView(R.id.tvComentContent)
        TextView tvComentContent;
        @BindView(R.id.tvTime)
        TextView tvTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
