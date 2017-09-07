package com.qtin.sexyvc.ui.main.fraghome.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jess.arms.utils.StringUtil;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.CustomApplication;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.ui.bean.InvestorEntity;
import com.qtin.sexyvc.ui.investor.InvestorDetailActivity;
import com.qtin.sexyvc.ui.widget.ratingbar.RatingBar;
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
    private MyBaseActivity activity;

    public HomeInvestorAdapter(Context context, ArrayList<InvestorEntity> data) {
        this.context = context;
        this.data = data;
        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
        mApplication = (CustomApplication) context.getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
        activity = (MyBaseActivity) context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_investor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        InvestorEntity entity = data.get(position);
        mImageLoader.loadImage(mApplication, GlideImageConfig
                .builder()
                .placeholder(R.drawable.avatar_blank)
                .errorPic(R.drawable.avatar_blank)
                .url(CommonUtil.getAbsolutePath(entity.getInvestor_avatar()))
                .transformation(new CropCircleTransformation(context))
                .imageView(holder.ivAvatar)
                .build());
        holder.tvName.setText(StringUtil.formatString(entity.getInvestor_name()));
        holder.tvFundName.setText(StringUtil.formatString(entity.getInvestor_title()));
        holder.tvInvestorTitle.setText(StringUtil.formatString(entity.getInvestor_title()));
        if (entity.getInvestor_uid() > 0) {
            holder.ivAnthStatus.setVisibility(View.VISIBLE);
        } else {
            holder.ivAnthStatus.setVisibility(View.GONE);
        }

        holder.llInvestorFocus.setVisibility(View.GONE);
        holder.llInvestorFeedBack.setVisibility(View.GONE);
        holder.llInvestorFollow.setVisibility(View.GONE);
        holder.llInvestorScore.setVisibility(View.GONE);


        if(position==0){
            holder.tvTopTitle.setText(context.getString(R.string.home_whole_best));
            holder.ivLeft.setImageResource(R.drawable.hots_title_1);
            holder.ivRight.setImageResource(R.drawable.hots_title_1);

            holder.llInvestorScore.setVisibility(View.VISIBLE);
            holder.tvRatingScore.setText("" + entity.getScore());
            holder.ratingScore.setRating(entity.getScore());

        }else if(position==1){
            holder.tvTopTitle.setText(context.getString(R.string.home_feedback_fastest));
            holder.ivLeft.setImageResource(R.drawable.hots_title_2_l);
            holder.ivRight.setImageResource(R.drawable.hots_title_2_r);

            holder.llInvestorFeedBack.setVisibility(View.VISIBLE);
            holder.pbFeedbackSpeed.setProgress(countRoadPercent(entity.getFeedback_agree(),entity.getFeedback_against()));

        }else if(position==2){
            holder.tvTopTitle.setText(context.getString(R.string.home_most_funs));
            holder.ivLeft.setImageResource(R.drawable.hots_title_3_l);
            holder.ivRight.setImageResource(R.drawable.hots_title_3_r);

            holder.llInvestorFollow.setVisibility(View.VISIBLE);
            holder.tvFollowNum.setText(""+entity.getFollow_number());

        }else if(position==3){
            holder.tvTopTitle.setText(context.getString(R.string.home_whole_focus));
            holder.ivLeft.setImageResource(R.drawable.hots_title_4_l);
            holder.ivRight.setImageResource(R.drawable.hots_title_4_r);

            holder.llInvestorFocus.setVisibility(View.VISIBLE);
            holder.tvCommentNum.setText(""+entity.getComment_number());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("investor_id", data.get(position).getInvestor_id());
                bundle.putBoolean("isFromFund", false);
                activity.gotoActivity(InvestorDetailActivity.class, bundle);
            }
        });
    }

    private int countRoadPercent(int feedback_agree,int feedback_against) {

        feedback_agree+=5;
        feedback_against+=5;

        int totle = feedback_agree + feedback_against;
        if (totle == 0) {
            return 0;
        } else {
            return feedback_agree * 100 / totle;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivLeft)
        ImageView ivLeft;
        @BindView(R.id.tvTopTitle)
        TextView tvTopTitle;
        @BindView(R.id.ivRight)
        ImageView ivRight;
        @BindView(R.id.ivAvatar)
        ImageView ivAvatar;
        @BindView(R.id.ivAnthStatus)
        ImageView ivAnthStatus;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvFundName)
        TextView tvFundName;
        @BindView(R.id.tvInvestorTitle)
        TextView tvInvestorTitle;
        @BindView(R.id.tvCommentNum)
        TextView tvCommentNum;
        @BindView(R.id.llInvestorFocus)
        LinearLayout llInvestorFocus;
        @BindView(R.id.tvFollowNum)
        TextView tvFollowNum;
        @BindView(R.id.llInvestorFollow)
        LinearLayout llInvestorFollow;
        @BindView(R.id.pbFeedbackSpeed)
        ProgressBar pbFeedbackSpeed;
        @BindView(R.id.llInvestorFeedBack)
        LinearLayout llInvestorFeedBack;
        @BindView(R.id.ratingScore)
        RatingBar ratingScore;
        @BindView(R.id.tvRatingScore)
        TextView tvRatingScore;
        @BindView(R.id.llInvestorScore)
        LinearLayout llInvestorScore;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
