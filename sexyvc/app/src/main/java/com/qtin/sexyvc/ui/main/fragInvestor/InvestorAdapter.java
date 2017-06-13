package com.qtin.sexyvc.ui.main.fragInvestor;

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
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.widget.DeviceUtils;
import com.qtin.sexyvc.ui.widget.TagContainer;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.utils.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/6/9.
 */

public class InvestorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<InvestorEntity> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public InvestorAdapter(Context context,ArrayList<InvestorEntity> data) {
        this.context=context;
        this.data = data;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        InvestorEntity entity=data.get(position);
        ViewHolder holder=(ViewHolder)viewHolder;

        holder.tvName.setText(StringUtil.formatString(entity.getInvestor_name()));
        holder.tvPosition.setText(StringUtil.formatString(entity.getFund_name()));
        holder.tvCommentNum.setText(context.getResources().getString(R.string.investor_join_comment)+entity.getCommentNum());
        holder.tvScore.setText(""+entity.getInvestor_recommendation_number());

        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .url(CommonUtil.getAbsolutePath(entity.getInvestor_avatar()))
                .transformation(new CropCircleTransformation(context))
                .imageView(holder.ivAvatar)
                .build());

        //评分
        holder.ratingScore.setRating(entity.getInvestor_recommendation_number());
        //标签
        TagContainer tagContainer=new TagContainer(context);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tagContainer.setLayoutParams(params);
        holder.tagLinearLayout.removeAllViews();
        holder.tagLinearLayout.addView(tagContainer);
        tagContainer.setStringValue(entity.getTagList(), (int) (DeviceUtils.getScreenSize(context).x- com.jess.arms.utils.DeviceUtils.dpToPixel(context,20)));
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPosition)
        TextView tvPosition;
        @BindView(R.id.tvCommentNum)
        TextView tvCommentNum;
        @BindView(R.id.ratingScore)
        BaseRatingBar ratingScore;
        @BindView(R.id.tvScore)
        TextView tvScore;
        @BindView(R.id.tagLinearLayout)
        LinearLayout tagLinearLayout;
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
