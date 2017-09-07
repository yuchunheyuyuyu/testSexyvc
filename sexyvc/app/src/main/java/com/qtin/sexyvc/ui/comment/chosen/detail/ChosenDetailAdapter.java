package com.qtin.sexyvc.ui.comment.chosen.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.CommentEntity;
import com.qtin.sexyvc.ui.bean.HomeInfoBean;
import com.qtin.sexyvc.ui.comment.detail.CommentDetailActivity;
import com.qtin.sexyvc.ui.main.fraghome.adapter.HomeAdapter;
import com.qtin.sexyvc.ui.subject.bean.DataTypeInterface;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.utils.AppStringUtil;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/9/7.
 */
public class ChosenDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DataTypeInterface> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private MyBaseActivity activity;

    public ChosenDetailAdapter(Context context,List<DataTypeInterface> data) {
        this.context = context;
        this.data = data;
        activity= (MyBaseActivity) context;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HomeAdapter.ITEM_COMMENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_topic_comment, parent, false);
            return new CommentHolder(view);
        } else if (viewType == DataTypeInterface.TYPE_TOPIC_CONTENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_topic_content, parent, false);
            return new TopicHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof CommentHolder){
            final CommentEntity entity= (CommentEntity) data.get(position);
            CommentHolder holder= (CommentHolder) viewHolder;
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
                    Bundle bundle=new Bundle();
                    bundle.putLong("comment_id",entity.getComment_id());
                    activity.gotoActivity(CommentDetailActivity.class,bundle);
                }
            });
            holder.tvPraiseNum.setText(""+entity.getLike());
            if(entity.getHas_praise()==0){
                holder.ivPraise.setSelected(false);
            }else{
                holder.ivPraise.setSelected(true);
            }
            holder.tvDate.setText(DateUtil.getSpecialDate(entity.getCreate_time()));

        }else if(viewHolder instanceof TopicHolder){
            HomeInfoBean homeInfoBean= (HomeInfoBean) data.get(position);
            TopicHolder holder= (TopicHolder) viewHolder;
            mImageLoader.loadImage(mApplication, GlideImageConfig
                    .builder()
                    .placeholder(R.drawable.logo_blank)
                    .errorPic(R.drawable.logo_blank)
                    .url(CommonUtil.getAbsolutePath(homeInfoBean.getCover_pic()))
                    .imageView(holder.ivTopic)
                    .build());
            holder.tvTopicTitle.setText(StringUtil.formatString(homeInfoBean.getTitle()));
            holder.tvTopicSummary.setText(StringUtil.formatString(homeInfoBean.getSummary()));
            holder.tvTopicIntro.setText(StringUtil.formatString(homeInfoBean.getIntro()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    static class CommentHolder extends RecyclerView.ViewHolder {
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

        CommentHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class TopicHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivTopic)
        RoundedImageView ivTopic;
        @BindView(R.id.tvTopicTitle)
        TextView tvTopicTitle;
        @BindView(R.id.tvTopicSummary)
        TextView tvTopicSummary;
        @BindView(R.id.tvTopicIntro)
        TextView tvTopicIntro;
        @BindView(R.id.topicContainer)
        RelativeLayout topicContainer;

        TopicHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
