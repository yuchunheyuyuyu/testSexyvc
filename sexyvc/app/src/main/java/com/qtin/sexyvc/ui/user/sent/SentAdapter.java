package com.qtin.sexyvc.ui.user.sent;

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
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.user.sent.bean.OnCommentClickListener;
import com.qtin.sexyvc.ui.user.sent.bean.SentBean;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
import com.qtin.sexyvc.utils.CommonUtil;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/8/14.
 */
public class SentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<SentBean> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private OnItemClickListener onItemClickListener;
    private OnCommentClickListener onCommentClickListener;

    public void setOnCommentClickListener(OnCommentClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SentAdapter(Context context, ArrayList<SentBean> data) {
        this.context = context;
        this.data = data;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder= (ViewHolder) viewHolder;
        SentBean bean=data.get(position);

        holder.tvName.setText(StringUtil.formatString(bean.getInvestor_name()));
        holder.tvTitle.setText(StringUtil.formatString(bean.getFund_name()));
        holder.ratingScore.setRating(bean.getScore());

        if(bean.getHas_roadshow()==0){
            holder.roadContainer.setSelected(true);
            holder.tvRoadComment.setSelected(true);
            holder.ivHasRoadComment.setVisibility(View.GONE);
        }else{
            holder.roadContainer.setSelected(false);
            holder.tvRoadComment.setSelected(false);
            holder.ivHasRoadComment.setVisibility(View.VISIBLE);
        }

        if(bean.getHas_comment()==0){
            holder.textContainer.setSelected(true);
            holder.tvTextComment.setSelected(true);
            holder.ivHasTextComment.setVisibility(View.GONE);
        }else{
            holder.textContainer.setSelected(false);
            holder.tvTextComment.setSelected(false);
            holder.ivHasTextComment.setVisibility(View.VISIBLE);
        }

        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .placeholder(R.drawable.avatar_blank)
                .errorPic(R.drawable.avatar_blank)
                .url(CommonUtil.getAbsolutePath(bean.getInvestor_avatar()))
                .transformation(new CropCircleTransformation(context))
                .imageView(holder.ivAvatar)
                .build());

        if (bean.getInvestor_uid() > 0) {
            holder.ivAnthStatus.setVisibility(View.VISIBLE);
        } else {
            holder.ivAnthStatus.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onClickItem(position);
                }
            }
        });
        holder.roadContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCommentClickListener!=null){
                    onCommentClickListener.onClickRoad(position);
                }
            }
        });
        holder.textContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCommentClickListener!=null){
                    onCommentClickListener.onClickText(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.ivAnthStatus)
        ImageView ivAnthStatus;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvRoadComment)
        TextView tvRoadComment;
        @BindView(R.id.ivHasRoadComment)
        ImageView ivHasRoadComment;
        @BindView(R.id.roadContainer)
        LinearLayout roadContainer;
        @BindView(R.id.tvTextComment)
        TextView tvTextComment;
        @BindView(R.id.ivHasTextComment)
        ImageView ivHasTextComment;
        @BindView(R.id.textContainer)
        LinearLayout textContainer;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
