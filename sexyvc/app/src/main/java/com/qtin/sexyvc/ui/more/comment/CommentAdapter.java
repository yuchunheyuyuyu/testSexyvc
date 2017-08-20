package com.qtin.sexyvc.ui.more.comment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailActivity;
import com.qtin.sexyvc.ui.investor.bean.CommentBean;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.utils.AppStringUtil;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.qtin.sexyvc.utils.DateUtil;

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
    private int unauth_count;
    private long contentId;

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public void setUnauth_count(int unauth_count) {
        this.unauth_count = unauth_count;
    }

    public CommentAdapter(Context context, ArrayList<CommentBean> data, int type) {
        this.context = context;
        this.data = data;
        this.type = type;
        activity = (MyBaseActivity) context;
    }

    @Override
    public int getItemViewType(int position) {
        if (unauth_count > 0 && position == data.size()) {
            return ConstantUtil.TYPE_UNAUTH;
        }
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //R.layout.item_has_load_all
        View view = null;
        if (viewType == ConstantUtil.TYPE_INVESTOR) {
            view = LayoutInflater.from(context).inflate(R.layout.item_investor_comment, parent, false);
            return new InvestorHolder(view);
        } else if (viewType == ConstantUtil.TYPE_FUND) {
            view = LayoutInflater.from(context).inflate(R.layout.item_fund_comment, parent, false);
            return new FundHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_unauth_count, parent, false);
            return new UnAuthHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof InvestorHolder) {
            dealInvestorComment(data.get(position), (InvestorHolder) viewHolder);
        } else if (viewHolder instanceof FundHolder) {
            dealFundComment(data.get(position), (FundHolder) viewHolder);
        } else if (viewHolder instanceof UnAuthHolder) {
            UnAuthHolder holder = (UnAuthHolder) viewHolder;
            String format = context.getString(R.string.format_unauth_count);
            holder.tvUnauthCount.setText(String.format(format, unauth_count));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT,type);
                    bundle.putLong(ConstantUtil.INTENT_ID,contentId);
                    bundle.putInt("auth_state",0);
                    bundle.putString(ConstantUtil.INTENT_TITLE,String.format(context.getString(R.string.format_unauth_count),unauth_count));
                    activity.gotoActivity(MoreCommentActivity.class,bundle);
                }
            });
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

        //加v的图标
        if (AppStringUtil.isShowVStatus(bean.getIs_anon(), bean.getU_auth_type(), bean.getU_auth_state())) {
            holder.ivIdentityStatus.setVisibility(View.VISIBLE);
            holder.ivIdentityStatus.setImageResource(AppStringUtil.getVStatusResourceId(bean.getU_auth_type()));
        } else {
            holder.ivIdentityStatus.setVisibility(View.GONE);
        }
        holder.tvTime.setText(DateUtil.getSpecialDate2(bean.getCreate_time()));
        holder.tvPraiseNum.setText(""+bean.getLike());
        if(bean.getHas_praise()==0){
            holder.ivPraise.setSelected(false);
        }else{
            holder.ivPraise.setSelected(true);
        }
    }

    private void dealInvestorComment(final CommentBean bean, InvestorHolder holder) {
        holder.ratingScore.setRating(bean.getScore());
        if (StringUtil.isBlank(bean.getDomain_name())) {
            holder.tvCommentTag.setVisibility(View.GONE);
        } else {
            holder.tvCommentTag.setVisibility(View.VISIBLE);
            holder.tvCommentTag.setText(bean.getDomain_name());
        }
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

        //加v的图标
        if (AppStringUtil.isShowVStatus(bean.getIs_anon(), bean.getU_auth_type(), bean.getU_auth_state())) {
            holder.ivIdentityStatus.setVisibility(View.VISIBLE);
            holder.ivIdentityStatus.setImageResource(AppStringUtil.getVStatusResourceId(bean.getU_auth_type()));
        } else {
            holder.ivIdentityStatus.setVisibility(View.GONE);
        }
        holder.tvTime.setText(DateUtil.getSpecialDate2(bean.getCreate_time()));
        holder.tvPraiseNum.setText(""+bean.getLike());
        if(bean.getHas_praise()==0){
            holder.ivPraise.setSelected(false);
        }else{
            holder.ivPraise.setSelected(true);
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            if (unauth_count > 0) {
                return data.size() + 1;
            } else {
                return data.size();
            }
        }
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
        @BindView(R.id.ivIdentityStatus)
        ImageView ivIdentityStatus;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvPraiseNum)
        TextView tvPraiseNum;
        @BindView(R.id.ivPraise)
        ImageView ivPraise;

        InvestorHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FundHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.ivIdentityStatus)
        ImageView ivIdentityStatus;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvPraiseNum)
        TextView tvPraiseNum;
        @BindView(R.id.ivPraise)
        ImageView ivPraise;

        FundHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class UnAuthHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvUnauthCount)
        TextView tvUnauthCount;

        UnAuthHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
