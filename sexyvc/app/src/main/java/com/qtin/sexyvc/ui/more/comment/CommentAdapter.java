package com.qtin.sexyvc.ui.more.comment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailActivity;
import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.utils.ConstantUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/7/7.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<CommentBean> data;
    private int type;//是投资人详情里面的评论还是投资机构中的评论
    private MyBaseActivity activity;

    public CommentAdapter(Context context, ArrayList<CommentBean> data, int type) {
        this.context = context;
        this.data = data;
        this.type = type;
        activity= (MyBaseActivity) context;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ConstantUtil.TYPE_INVESTOR) {
            view = LayoutInflater.from(context).inflate(R.layout.item_investor_comment, parent, false);
            return new InvestorHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_fund_comment, parent, false);
            return new FundHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof InvestorHolder){
            dealInvestorComment(data.get(position), (InvestorHolder) viewHolder);
        }else{
            dealFundComment(data.get(position), (FundHolder) viewHolder);
        }
    }

    private void dealFundComment(final CommentBean bean, FundHolder holder) {
        holder.ratingScore.setRating(bean.getScore());
        if (StringUtil.isBlank(bean.getDomain_name())) {
            holder.tvCommentTag.setVisibility(View.GONE);
        } else {
            holder.tvCommentTag.setVisibility(View.VISIBLE);
            holder.tvCommentTag.setText(bean.getDomain_name());
        }
        holder.tvTo.setText(StringUtil.formatString(bean.getInvestor_name()));
        holder.tvFrom.setText(StringUtil.formatString(bean.getU_nickname()));
        holder.tvTitle.setText(StringUtil.formatString(bean.getTitle()));
        holder.tvContent.setText(StringUtil.formatString(bean.getContent()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("comment_id", bean.getComment_id());
                activity.gotoActivity(CommentDetailActivity.class, bundle);
            }
        });
    }

    private void dealInvestorComment(final CommentBean bean, InvestorHolder holder) {
        holder.ratingScore.setRating(bean.getScore());
        if(StringUtil.isBlank(bean.getDomain_name())){
            holder.tvCommentTag.setVisibility(View.GONE);
        }else{
            holder.tvCommentTag.setVisibility(View.VISIBLE);
            holder.tvCommentTag.setText(bean.getDomain_name());
        }
        holder.tvFrom.setText(StringUtil.formatString(bean.getU_nickname()));
        holder.tvTitle.setText(StringUtil.formatString(bean.getTitle()));
        holder.tvContent.setText(StringUtil.formatString(bean.getContent()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putLong("comment_id",bean.getComment_id());
                activity.gotoActivity(CommentDetailActivity.class,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class InvestorHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCommentTag)
        TextView tvCommentTag;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.lineBottom)
        View lineBottom;

        InvestorHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FundHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvCommentTag)
        TextView tvCommentTag;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.tvTo)
        TextView tvTo;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.lineBottom)
        View lineBottom;

        FundHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
