package com.qtin.sexyvc.ui.comment.chosen.frag;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.ui.bean.OnItemClickListener;
import com.qtin.sexyvc.ui.bean.TopicBean;
import com.qtin.sexyvc.utils.CommonUtil;
import com.qtin.sexyvc.utils.DateUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 17/7/19.
 */

public class CommentChosenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<TopicBean> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CommentChosenAdapter(Context context,ArrayList<TopicBean> data) {
        this.data = data;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        TopicBean entity = data.get(position);

        String currentDate= DateUtil.getDateExpression4(entity.getCreate_time());
        if(position==0){
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.tvDate.setText(currentDate);
        }else{
            String lastDate=DateUtil.getDateExpression4(data.get(position-1).getCreate_time());
            if(currentDate.equals(lastDate)){
                holder.tvDate.setVisibility(View.GONE);
            }else{
                holder.tvDate.setVisibility(View.VISIBLE);
                holder.tvDate.setText(currentDate);
            }
        }
        holder.tvDate.setText(currentDate);

        holder.tvTopicTitle.setText(StringUtil.formatString(entity.getTitle()));
        holder.tvTopicSummary.setText(StringUtil.formatString(entity.getSummary()));
        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .url(CommonUtil.getAbsolutePath(entity.getCover_pic()))
                .imageView(holder.ivTopic)
                .build());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.ivTopic)
        RoundedImageView ivTopic;
        @BindView(R.id.tvTopicTitle)
        TextView tvTopicTitle;
        @BindView(R.id.tvTopicSummary)
        TextView tvTopicSummary;
        @BindView(R.id.topicContainer)
        RelativeLayout topicContainer;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
