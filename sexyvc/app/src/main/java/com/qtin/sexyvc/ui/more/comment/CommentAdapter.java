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
import com.qtin.sexyvc.ui.more.UnAuthCommentActivity;
import com.qtin.sexyvc.ui.road.show.RoadDetailActivity;
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
        View view = null;
        if (viewType == ConstantUtil.TYPE_INVESTOR) {
            view = LayoutInflater.from(context).inflate(R.layout.item_investor_text_comment, parent, false);
            return new InvestorHolder(view);
        } else if (viewType == ConstantUtil.TYPE_FUND) {
            view = LayoutInflater.from(context).inflate(R.layout.item_fund_comment, parent, false);
            return new FundHolder(view);
        } else if (viewType == ConstantUtil.TYPE_INVESTOR_ROAD) {
            view = LayoutInflater.from(context).inflate(R.layout.item_investor_road_comment, parent, false);
            return new InvestorRoadHolder(view);
        } else if (viewType == ConstantUtil.TYPE_FUND_ROAD) {
            view = LayoutInflater.from(context).inflate(R.layout.item_fund_road_comment, parent, false);
            return new FundRoadHolder(view);
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
            String format="";
            if(type == ConstantUtil.TYPE_FUND||type == ConstantUtil.TYPE_INVESTOR){
                format = context.getString(R.string.format_unauth_count);
            }else{
                format = context.getString(R.string.format_unauth_count_evaluate);
            }
            holder.tvUnauthCount.setText(String.format(format, unauth_count));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ConstantUtil.TYPE_INVESTOR_FUND_INTENT, type);
                    bundle.putLong(ConstantUtil.INTENT_ID, contentId);

                    String format="";
                    if(type == ConstantUtil.TYPE_FUND||type == ConstantUtil.TYPE_INVESTOR){
                        format = context.getString(R.string.format_unauth_count);
                    }else{
                        format = context.getString(R.string.format_unauth_count_evaluate);
                    }
                    bundle.putString(ConstantUtil.INTENT_TITLE, String.format(format, unauth_count));
                    activity.gotoActivity(UnAuthCommentActivity.class, bundle);
                }
            });
        }else if(viewHolder instanceof FundRoadHolder){
            dealFundRoad(data.get(position), (FundRoadHolder) viewHolder);
        }else if(viewHolder instanceof InvestorRoadHolder){
            dealInvestorRoad(data.get(position), (InvestorRoadHolder) viewHolder);
        }
    }

    private void dealInvestorRoad(final CommentBean bean,InvestorRoadHolder holder){
        holder.ratingScore.setRating(bean.getScore());
        if(StringUtil.isBlank(bean.getU_stage_name())){
            holder.tvStageTag.setVisibility(View.GONE);
        }else{
            holder.tvStageTag.setVisibility(View.VISIBLE);
            holder.tvStageTag.setText(bean.getU_stage_name());
        }

        if(StringUtil.isBlank(bean.getU_domain_name())){
            holder.tvDomainTag.setVisibility(View.GONE);
        }else{
            holder.tvDomainTag.setVisibility(View.VISIBLE);
            holder.tvDomainTag.setText(bean.getU_domain_name());
        }
        if(bean.getIs_anon()==0){
            holder.tvFrom.setSelected(true);
        }else{
            holder.tvFrom.setSelected(false);
        }

        holder.tvFrom.setText(StringUtil.formatString(bean.getU_nickname()));
        //加v的图标
        if (AppStringUtil.isShowVStatus(bean.getIs_anon(), bean.getU_auth_type(), bean.getU_auth_state())) {
            holder.ivIdentityStatus.setVisibility(View.VISIBLE);
            holder.ivIdentityStatus.setImageResource(AppStringUtil.getVStatusResourceId(bean.getU_auth_type()));
        } else {
            holder.ivIdentityStatus.setVisibility(View.GONE);
        }
        //holder.tvTo.setText(StringUtil.formatString(bean.getInvestor_name()));
        if(StringUtil.isBlank(bean.getSource())){
            holder.tvSource.setText(context.getString(R.string.from_sexyvc));
        }else{
            holder.tvSource.setText(bean.getSource());
        }

        holder.tvTime.setText(DateUtil.getSpecialDate(bean.getCreate_time()));

        holder.tvPraiseNum.setText("" + bean.getLike());
        if (bean.getHas_praise() == 0) {
            holder.ivPraise.setSelected(false);
        } else {
            holder.ivPraise.setSelected(true);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putLong(ConstantUtil.INTENT_ID,bean.getId());
                activity.gotoActivity(RoadDetailActivity.class, bundle);
            }
        });
    }

    private void dealFundRoad(final CommentBean bean,FundRoadHolder holder){
        holder.ratingScore.setRating(bean.getScore());
        if(StringUtil.isBlank(bean.getU_stage_name())){
            holder.tvStageTag.setVisibility(View.GONE);
        }else{
            holder.tvStageTag.setVisibility(View.VISIBLE);
            holder.tvStageTag.setText(bean.getU_stage_name());
        }

        if(StringUtil.isBlank(bean.getU_domain_name())){
            holder.tvDomainTag.setVisibility(View.GONE);
        }else{
            holder.tvDomainTag.setVisibility(View.VISIBLE);
            holder.tvDomainTag.setText(bean.getU_domain_name());
        }
        if(bean.getIs_anon()==0){
            holder.tvFrom.setSelected(true);
        }else{
            holder.tvFrom.setSelected(false);
        }

        holder.tvFrom.setText(StringUtil.formatString(bean.getU_nickname()));
        //加v的图标
        if (AppStringUtil.isShowVStatus(bean.getIs_anon(), bean.getU_auth_type(), bean.getU_auth_state())) {
            holder.ivIdentityStatus.setVisibility(View.VISIBLE);
            holder.ivIdentityStatus.setImageResource(AppStringUtil.getVStatusResourceId(bean.getU_auth_type()));
        } else {
            holder.ivIdentityStatus.setVisibility(View.GONE);
        }
        holder.tvTo.setText(StringUtil.formatString(bean.getInvestor_name()));
        holder.tvTime.setText(DateUtil.getSpecialDate(bean.getCreate_time()));
        if(StringUtil.isBlank(bean.getSource())){
            holder.tvSource.setText(context.getString(R.string.from_sexyvc));
        }else{
            holder.tvSource.setText(bean.getSource());
        }

        holder.tvPraiseNum.setText("" + bean.getLike());
        if (bean.getHas_praise() == 0) {
            holder.ivPraise.setSelected(false);
        } else {
            holder.ivPraise.setSelected(true);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putLong(ConstantUtil.INTENT_ID,bean.getId());
                activity.gotoActivity(RoadDetailActivity.class, bundle);
            }
        });
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
        holder.tvTime.setText(DateUtil.getSpecialDate(bean.getCreate_time()));
        holder.tvPraiseNum.setText("" + bean.getLike());
        if (bean.getHas_praise() == 0) {
            holder.ivPraise.setSelected(false);
        } else {
            holder.ivPraise.setSelected(true);
        }

        if(bean.getIs_anon()==0){
            holder.tvFrom.setSelected(true);
        }else{
            holder.tvFrom.setSelected(false);
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
        holder.tvTime.setText(DateUtil.getSpecialDate(bean.getCreate_time()));
        holder.tvPraiseNum.setText("" + bean.getLike());
        if (bean.getHas_praise() == 0) {
            holder.ivPraise.setSelected(false);
        } else {
            holder.ivPraise.setSelected(true);
        }
        if(bean.getIs_anon()==0){
            holder.tvFrom.setSelected(true);
        }else{
            holder.tvFrom.setSelected(false);
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

    static class InvestorRoadHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvStageTag)
        TextView tvStageTag;
        @BindView(R.id.tvDomainTag)
        TextView tvDomainTag;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.ivIdentityStatus)
        ImageView ivIdentityStatus;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvSource)
        TextView tvSource;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvPraiseNum)
        TextView tvPraiseNum;
        @BindView(R.id.ivPraise)
        ImageView ivPraise;

        InvestorRoadHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FundRoadHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvStageTag)
        TextView tvStageTag;
        @BindView(R.id.tvDomainTag)
        TextView tvDomainTag;
        @BindView(R.id.tvFrom)
        TextView tvFrom;
        @BindView(R.id.ivIdentityStatus)
        ImageView ivIdentityStatus;
        @BindView(R.id.tvTo)
        TextView tvTo;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvSource)
        TextView tvSource;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvPraiseNum)
        TextView tvPraiseNum;
        @BindView(R.id.ivPraise)
        ImageView ivPraise;

        FundRoadHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
