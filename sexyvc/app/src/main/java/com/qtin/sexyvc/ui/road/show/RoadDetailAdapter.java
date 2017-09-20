package com.qtin.sexyvc.ui.road.show;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.DetailClickListener;
import com.qtin.sexyvc.ui.bean.ReplyBean;
import com.qtin.sexyvc.ui.road.show.bean.RoadDetailBean;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.widget.RoadView;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.utils.AppStringUtil;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/6/28.
 */
public class RoadDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DataTypeInterface> data;
    private MyBaseActivity activity;
    private DetailClickListener clickListener;

    public void setClickListener(DetailClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public RoadDetailAdapter(Context context, List<DataTypeInterface> data) {
        this.context = context;
        this.data = data;
        activity = (MyBaseActivity) context;

        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == DataTypeInterface.TYPE_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_road_detail_content, parent, false);
            return new ContentHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_COMMENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_reply, parent, false);
            return new ReplyHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ContentHolder) {
            ContentHolder holder = (ContentHolder) viewHolder;
            RoadDetailBean bean = (RoadDetailBean) data.get(position);

            if(StringUtil.isBlank(bean.getU_domain_name())){
                holder.tvDomainTag.setVisibility(View.GONE);
            }else{
                holder.tvDomainTag.setVisibility(View.VISIBLE);
                holder.tvDomainTag.setText(bean.getU_domain_name());
            }
            if(StringUtil.isBlank(bean.getU_stage_name())){
                holder.tvStageTag.setVisibility(View.GONE);
            }else{
                holder.tvStageTag.setVisibility(View.VISIBLE);
                holder.tvStageTag.setText(bean.getU_stage_name());
            }
            if(StringUtil.isBlank(bean.getSource())){
                holder.tvRource.setText(context.getString(R.string.from_sexyvc));
            }else{
                holder.tvRource.setText(bean.getSource());
            }
            //评分
            holder.ratingScore.setRating(bean.getScore());
            //点赞
            if (bean.getHas_praise() == 0) {
                holder.ivPraise.setSelected(false);
            } else {
                holder.ivPraise.setSelected(true);
            }
            holder.tvPraiseNum.setText("" + bean.getLike());

            holder.tvFrom.setText(StringUtil.formatString(bean.getU_nickname()));

            //加v的图标
            if (AppStringUtil.isShowVStatus(bean.getIs_anon(), bean.getU_auth_type(), bean.getU_auth_state())) {
                holder.ivIdentityStatus.setVisibility(View.VISIBLE);
                holder.ivIdentityStatus.setImageResource(AppStringUtil.getVStatusResourceId(bean.getU_auth_type()));
            } else {
                holder.ivIdentityStatus.setVisibility(View.GONE);
            }

            long time = bean.getCreate_time();
            holder.tvDate.setText(DateUtil.getSpecialDate(time));
            if (bean.getReply_count() == 0) {
                holder.tvCommentCount.setText(context.getResources().getString(R.string.has_no_reply));
            } else {
                holder.tvCommentCount.setText(bean.getReply_count() + context.getResources().getString(R.string.reply_count));
            }

            holder.ivPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClickDetailPraise(-1);
                    }
                }
            });
            holder.investorContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClickInvestor();
                    }
                }
            });

            mImageLoader.loadImage(mApplication, GlideImageConfig
                    .builder()
                    .placeholder(R.drawable.avatar_blank)
                    .errorPic(R.drawable.avatar_blank)
                    .transformation(new CropCircleTransformation(context))
                    .url(CommonUtil.getAbsolutePath(bean.getInvestor_avatar()))
                    .imageView(holder.ivTargetAvatar)
                    .build());
            holder.tvTargetName.setText(StringUtil.formatString(bean.getInvestor_name()));
            holder.tvTargetTitle.setText(StringUtil.formatAtString(bean.getFund_name()));
            holder.ratingTargetScore.setRating(bean.getInvestor_score());
            holder.tvTargetRate.setText("" + bean.getInvestor_score());

            if (bean.getInvestor_uid() == 0) {
                holder.ivTargetAnthStatus.setVisibility(View.GONE);
            } else {
                holder.ivTargetAnthStatus.setVisibility(View.VISIBLE);
            }
            holder.roadView.setData(bean.getAnswer_content());

        } else if (viewHolder instanceof ReplyHolder) {
            ReplyHolder commentHolder = (ReplyHolder) viewHolder;
            final ReplyBean entity = (ReplyBean) data.get(position);

            //加v的图标
            if (AppStringUtil.isShowVStatus(entity.getIs_anon(), entity.getU_auth_type(), entity.getU_auth_state())) {
                commentHolder.ivIdentityStatus.setVisibility(View.VISIBLE);
                commentHolder.ivIdentityStatus.setImageResource(AppStringUtil.getVStatusResourceId(entity.getU_auth_type()));
            } else {
                commentHolder.ivIdentityStatus.setVisibility(View.GONE);
            }

            if (entity.getHas_praise() == 0) {
                commentHolder.ivPraise.setSelected(false);
            } else {
                commentHolder.ivPraise.setSelected(true);
            }
            commentHolder.tvContent.setText(StringUtil.formatString(entity.getReply_content()));
            commentHolder.tvNick.setText(StringUtil.formatString(entity.getU_nickname()));

            if(entity.getLike()==0){
                commentHolder.tvPraiseNum.setText("");
            }else{
                commentHolder.tvPraiseNum.setText("" + entity.getLike());
            }

            commentHolder.tvTime.setText(DateUtil.getSpecialDate(entity.getCreate_time()));
            if(entity.getIs_anon()==1){
                entity.setU_avatar("");
            }
            mImageLoader.loadImage(mApplication, GlideImageConfig
                    .builder()
                    .placeholder(R.drawable.avatar_blank)
                    .errorPic(R.drawable.avatar_blank)
                    .transformation(new CropCircleTransformation(context))
                    .url(CommonUtil.getAbsolutePath(entity.getU_avatar()))
                    .imageView(commentHolder.ivAvatar)
                    .build());

            commentHolder.ivPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClickItemPraise(position);
                    }
                }
            });
            commentHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClickItemReply(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ReplyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.tvNick)
        TextView tvNick;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvPraiseNum)
        TextView tvPraiseNum;
        @BindView(R.id.ivPraise)
        ImageView ivPraise;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.ivIdentityStatus)
        ImageView ivIdentityStatus;

        ReplyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ContentHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.tvRource)
        TextView tvRource;
        @BindView(R.id.roadView)
        RoadView roadView;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvPraiseNum)
        TextView tvPraiseNum;
        @BindView(R.id.ivPraise)
        ImageView ivPraise;
        @BindView(R.id.ivTargetAvatar)
        ImageView ivTargetAvatar;
        @BindView(R.id.ivTargetAnthStatus)
        ImageView ivTargetAnthStatus;
        @BindView(R.id.tvTargetName)
        TextView tvTargetName;
        @BindView(R.id.tvTargetTitle)
        TextView tvTargetTitle;
        @BindView(R.id.ratingTargetScore)
        RatingBar ratingTargetScore;
        @BindView(R.id.tvTargetRate)
        TextView tvTargetRate;
        @BindView(R.id.investorContainer)
        LinearLayout investorContainer;
        @BindView(R.id.tvCommentCount)
        TextView tvCommentCount;

        ContentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
