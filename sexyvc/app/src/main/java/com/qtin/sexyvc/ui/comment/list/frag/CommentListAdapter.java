package com.qtin.sexyvc.ui.comment.list.frag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.utils.AppStringUtil;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_comment, parent, false);
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

        String name=entity.getInvestor_name()+"@"+entity.getFund_name();
        holder.tvTarget.setText(AppStringUtil.getLimitString(name));

        holder.ratingScore.setRating(entity.getScore());
        holder.tvCommentTitle.setText(StringUtil.formatString(entity.getTitle()));
        holder.tvComentContent.setText(StringUtil.formatString(entity.getContent()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onClickItem(position);
                }
            }
        });
        holder.tvPraiseNum.setText(""+entity.getLike());
        if(entity.getHas_praise()==0){
            holder.ivPraise.setSelected(false);
        }else{
            holder.ivPraise.setSelected(true);
        }
        holder.tvDate.setText(DateUtil.getSpecialDate(entity.getCreate_time()));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvCommentTag)
        TextView tvCommentTag;
        @BindView(R.id.tvTarget)
        TextView tvTarget;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvCommentTitle)
        TextView tvCommentTitle;
        @BindView(R.id.tvComentContent)
        TextView tvComentContent;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvPraiseNum)
        TextView tvPraiseNum;
        @BindView(R.id.ivPraise)
        ImageView ivPraise;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
