package com.qtin.sexyvc.ui.main.fraghome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.widget.rating.BaseRatingBar;
import com.qtin.sexyvc.utils.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ls on 17/5/9.
 */

public class HomeInvestorAdapter extends RecyclerView.Adapter<HomeInvestorAdapter.ViewHolder> {

    private Context context;
    private ArrayList<InvestorEntity> data;
    private final CustomApplication mApplication;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架

    public HomeInvestorAdapter(Context context, ArrayList<InvestorEntity> data) {
        this.context = context;
        this.data = data;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_investor_inner_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        InvestorEntity entity = data.get(position);
        if (position == data.size() - 1) {
            holder.lineRight.setVisibility(View.VISIBLE);
        } else {
            holder.lineRight.setVisibility(View.GONE);
        }
        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .url(CommonUtil.getAbsolutePath(entity.getInvestor_avatar()))
                .transformation(new CropCircleTransformation(context))
                .imageView(holder.ivAvatar)
                .build());
        holder.tvInvestorName.setText(StringUtil.formatString(entity.getInvestor_name()));
        holder.tvFundName.setText(StringUtil.formatString(entity.getInvestor_title()));
        holder.ratingScore.setRating(entity.getInvestor_recommendation_number());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.lineLeft)
        View lineLeft;
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.tvInvestorName)
        TextView tvInvestorName;
        @BindView(R.id.tvFundName)
        TextView tvFundName;
        @BindView(R.id.ratingScore)
        BaseRatingBar ratingScore;
        @BindView(R.id.lineRight)
        View lineRight;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
