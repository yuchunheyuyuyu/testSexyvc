package com.qtin.sexyvc.ui.comment.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
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
import com.qtin.sexyvc.ui.bean.AdditionalBean;
import com.qtin.sexyvc.ui.bean.DetailClickListener;
import com.qtin.sexyvc.ui.bean.ReplyBean;
import com.qtin.sexyvc.ui.comment.detail.bean.CommentContentBean;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.widget.DeviceUtils;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.DateUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/6/28.
 */
public class CommentDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DataTypeInterface> data;
    private MyBaseActivity activity;
    private DetailClickListener clickListener;

    public void setClickListener(DetailClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public CommentDetailAdapter(Context context, ArrayList<DataTypeInterface> data) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_comment_detail_content, parent, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,final int position) {
        if(viewHolder instanceof ContentHolder){
            ContentHolder holder= (ContentHolder) viewHolder;
            CommentContentBean bean= (CommentContentBean) data.get(position);

            //标签
            if(StringUtil.isBlank(bean.getType_name())){
                holder.tvCommentTag.setVisibility(View.GONE);
            }else{
                holder.tvCommentTag.setVisibility(View.VISIBLE);
                holder.tvCommentTag.setText(bean.getType_name());
            }

            //追加评论
            ArrayList<AdditionalBean> additional=bean.getAdditional();
            if(additional==null||additional.isEmpty()){
                holder.additionalContainer.setVisibility(View.GONE);
            }else{
                holder.additionalContainer.setVisibility(View.VISIBLE);
                holder.additionalContainer.removeAllViews();

                for(AdditionalBean a:additional){
                    //追加的内容
                    TextView tvContent=new TextView(context);
                    tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    tvContent.setLineSpacing(DeviceUtils.sp2px(context,6),1);

                    SpannableStringBuilder stringBuilder=new SpannableStringBuilder();

                    stringBuilder.append(context.getResources().getString(R.string.additional));
                    int start=stringBuilder.length();
                    ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.black70));
                    stringBuilder.setSpan(span,0,start, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                    stringBuilder.append(a.getContent());
                    ForegroundColorSpan span2 = new ForegroundColorSpan(context.getResources().getColor(R.color.black90));
                    stringBuilder.setSpan(span,start,stringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);


                    tvContent.setText(stringBuilder);

                    //时间
                    TextView tvTime=new TextView(context);
                    tvTime.setTextColor(context.getResources().getColor(R.color.black30));
                    tvTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    tvTime.setPadding(0,DeviceUtils.dip2px(context,3),0,DeviceUtils.dip2px(context,10));
                    tvTime.setText(DateUtil.getSpecialDate(a.getCreate_time()));

                    holder.additionalContainer.addView(tvContent);
                    holder.additionalContainer.addView(tvTime);
                }

            }

            //评分
            holder.ratingScore.setRating(bean.getScore());
            //点赞
            if(bean.getHas_praise()==0){
                holder.ivPraise.setSelected(false);
            }else{
                holder.ivPraise.setSelected(true);
            }
            holder.tvPraiseNum.setText(""+bean.getPraise_count());

            holder.tvFrom.setText(StringUtil.formatString(bean.getU_nickname()));
            holder.tvTitle.setText(StringUtil.formatString(bean.getTitle()));
            holder.tvContent.setText(StringUtil.formatString(bean.getContent()));
            long time=0;
            try{
                time=Long.parseLong(bean.getCreate_time());
            }catch(Exception e){
                e.printStackTrace();
            }
            holder.tvDate.setText(DateUtil.getSpecialDate(time));
            if(bean.getReply_count()==0){
                holder.tvCommentCount.setText(context.getResources().getString(R.string.has_no_comment));
            }else{
                holder.tvCommentCount.setText(bean.getReply_count()+context.getResources().getString(R.string.reply_count));
            }

            holder.ivPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener!=null){
                        clickListener.onClickDetailPraise(-1);
                    }
                }
            });

        }else if(viewHolder instanceof ReplyHolder){
            ReplyHolder commentHolder = (ReplyHolder) viewHolder;
            final ReplyBean entity = (ReplyBean) data.get(position);

            if (entity.getHas_praise() == 0) {
                commentHolder.ivPraise.setSelected(false);
            } else {
                commentHolder.ivPraise.setSelected(true);
            }
            commentHolder.tvContent.setText(StringUtil.formatString(entity.getReply_content()));
            commentHolder.tvNick.setText(StringUtil.formatString(entity.getU_nickname()));
            commentHolder.tvPraiseNum.setText("" + entity.getLike());

            commentHolder.tvTime.setText(DateUtil.getSpecialDate(entity.getCreate_time()));

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
                    if(clickListener!=null){
                        clickListener.onClickItemPraise(position);
                    }
                }
            });
            commentHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener!=null){
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

    static class ContentHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvPraiseNum)
        TextView tvPraiseNum;
        @BindView(R.id.ivPraise)
        ImageView ivPraise;
        @BindView(R.id.additionalContainer)
        LinearLayout additionalContainer;
        @BindView(R.id.tvCommentCount)
        TextView tvCommentCount;

        ContentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ReplyHolder extends RecyclerView.ViewHolder{
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

        ReplyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
